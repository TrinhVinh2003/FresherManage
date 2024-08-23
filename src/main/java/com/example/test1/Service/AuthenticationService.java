package com.example.test1.Service;

import com.example.test1.Dto.request.AuthenticationRequest;
import com.example.test1.Dto.request.IntrospectRequest;
import com.example.test1.Dto.request.LogoutRequest;
import com.example.test1.Dto.response.AuthenticationResponse;
import com.example.test1.Dto.response.IntrospectReponse;
import com.example.test1.Entity.InvalidatedToken;
import com.example.test1.Entity.User;
import com.example.test1.Exception.AppException;
import com.example.test1.Exception.ErrorCode;
import com.example.test1.repository.InvalidatedTokenRepository;
import com.example.test1.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AuthenticationService {
    private final UserRepository userRepository;
    InvalidatedTokenRepository invalidatedTokenRepository;
    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY ;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;

    public IntrospectReponse introspect(IntrospectRequest request) throws JOSEException , ParseException {
        var token = request.getToken();
        boolean isValid = true;

        try {
            verifyToken(token);
        } catch (AppException e){
            isValid = false;
        }

        return IntrospectReponse.builder().valid(isValid).build();

    }
    public AuthenticationResponse authenticate(AuthenticationRequest request){
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
        PasswordEncoder passwordEncoder =  new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPassword(),user.getPassword());
        if (!authenticated)
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        var token = generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();

    }

    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        var signToken = verifyToken(request.getToken());
        String jit = signToken.getJWTClaimsSet().getJWTID();
        Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jit)
                .expiryTime(expiryTime)
                .build();
        invalidatedTokenRepository.save(invalidatedToken);

    }
    private SignedJWT verifyToken(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date()))) throw new AppException(ErrorCode.UNAUTHENTICATED);

        if(invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw  new AppException(ErrorCode.UNAUTHENTICATED);
        return signedJWT;
    }
    private String generateToken(User username){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username.getUsername())
                .issuer("vinh.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope",buildScope(username))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsOject  = new JWSObject(header,payload);
        try {
            jwsOject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsOject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token");
            throw new RuntimeException(e);
        }

    }

    private String buildScope(User user){
        StringJoiner stringJoiner = new StringJoiner(" ");
        if(!CollectionUtils.isEmpty(user.getRoles())){
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());
                if(!CollectionUtils.isEmpty(role.getPermissions())){
                    role.getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));
                }
            });
        }
        return stringJoiner.toString();

    }

}

