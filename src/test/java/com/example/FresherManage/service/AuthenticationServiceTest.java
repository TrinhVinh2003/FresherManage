package com.example.FresherManage.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.text.ParseException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.FresherManage.Dto.request.AuthenticationRequest;
import com.example.FresherManage.Dto.request.IntrospectRequest;
import com.example.FresherManage.Dto.response.AuthenticationResponse;
import com.example.FresherManage.Dto.response.IntrospectReponse;
import com.example.FresherManage.Service.impl.AuthenticationService;
import com.example.FresherManage.domain.Entity.User;
import com.example.FresherManage.domain.Exception.AppException;
import com.example.FresherManage.domain.Exception.ErrorCode;
import com.example.FresherManage.repository.InvalidatedTokenRepository;
import com.example.FresherManage.repository.UserRepository;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;

class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private InvalidatedTokenRepository invalidatedTokenRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set the SIGNER_KEY using ReflectionTestUtils
        String signerKey = "1TjXchw5FloESb63Kc+DFhTARvpWL4jUGCwfGWxuG5SIf/1y/LgJxHnMqaF6A/ij";
        ReflectionTestUtils.setField(authenticationService, "SIGNER_KEY", signerKey);
    }

    @Test
    void testAuthenticateSuccess() {
        // Setup mock user
        User mockUser = new User();
        mockUser.setUsername("admin");
        mockUser.setPassword(new BCryptPasswordEncoder(10).encode("admin"));
        userRepository.save(mockUser);
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(mockUser));

        AuthenticationRequest request = new AuthenticationRequest("admin", "admin");
        AuthenticationResponse response = authenticationService.authenticate(request);

        assertNotNull(response);
        assertTrue(response.isAuthenticated());
        assertNotNull(response.getToken());
        verify(userRepository, times(1)).findByUsername("admin");
    }

    @Test
    void testAuthenticateFailureUserNotFound() {
        when(userRepository.findByUsername("admin")).thenReturn(Optional.empty());

        AuthenticationRequest request = new AuthenticationRequest("nonexistent", "password");

        AppException exception = assertThrows(AppException.class, () -> authenticationService.authenticate(request));
        assertEquals(ErrorCode.USER_NOT_EXIST, exception.getErrorCode());
        verify(userRepository, times(1)).findByUsername("nonexistent");
    }

    @Test
    void testAuthenticateFailureWrongPassword() {
        User mockUser = new User();
        mockUser.setUsername("vinh");
        mockUser.setPassword(new BCryptPasswordEncoder(10).encode("534473"));
        userRepository.save(mockUser);
        when(userRepository.findByUsername("vinh")).thenReturn(Optional.of(mockUser));

        AuthenticationRequest request = new AuthenticationRequest("vinh", "wrong_password");

        AppException exception = assertThrows(AppException.class, () -> authenticationService.authenticate(request));
        assertEquals(ErrorCode.UNAUTHENTICATED, exception.getErrorCode());
        verify(userRepository, times(1)).findByUsername("vinh");
    }

    @Test
    void testGenerateToken() {
        User mockUser = new User();
        mockUser.setUsername("testuser");

        String token = authenticationService.generateToken(mockUser);
        assertNotNull(token);
    }

    @Test
    void testVerifyTokenValid() throws JOSEException, ParseException {
        String mockToken =
                "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJ2aW5oLmNvbSIsInN1YiI6ImFkbWluIiwiZXhwIjoxNzI1NzMwMjk4LCJpYXQiOjE3MjU3MjY2OTgsImp0aSI6IjlmYWI4YTRkLTNlZWItNDQ1Yi05NzVkLTNhYjdlYmQ0MjM3NSIsInNjb3BlIjoiUk9MRV9BRE1JTiJ9.C_prq4ksV8kQZ7LbOnwopHph2e49v4eCQzogJPsPo6sZGIdPGCMNY13ri2GnKNoiEhPtS60dbwA2RZ0nJvbFmQ";

        SignedJWT signedJWT = SignedJWT.parse(mockToken);

        // Ensure token is verified successfully (mock repository behavior if needed)
        when(invalidatedTokenRepository.existsById(anyString())).thenReturn(false);

        SignedJWT verifiedJWT = authenticationService.verifyToken(mockToken, false);
        assertNotNull(verifiedJWT);
        assertEquals("admin", verifiedJWT.getJWTClaimsSet().getSubject());
    }

    @Test
    void testIntrospectValidToken() throws JOSEException, ParseException {
        String mockToken =
                "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJ2aW5oLmNvbSIsInN1YiI6ImFkbWluIiwiZXhwIjoxNzI1NzMwMjk4LCJpYXQiOjE3MjU3MjY2OTgsImp0aSI6IjlmYWI4YTRkLTNlZWItNDQ1Yi05NzVkLTNhYjdlYmQ0MjM3NSIsInNjb3BlIjoiUk9MRV9BRE1JTiJ9.C_prq4ksV8kQZ7LbOnwopHph2e49v4eCQzogJPsPo6sZGIdPGCMNY13ri2GnKNoiEhPtS60dbwA2RZ0nJvbFmQ";
        when(invalidatedTokenRepository.existsById(anyString())).thenReturn(false);

        IntrospectRequest request = new IntrospectRequest(mockToken);
        IntrospectReponse response = authenticationService.introspect(request);

        assertTrue(response.isValid());
    }

    @Test
    void testIntrospectInvalidToken() throws JOSEException, ParseException {
        String mockToken =
                "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJ2aW5oLmNvbSIsInN1YiI6ImFkbWluIiwiZXhwIjoxNzI1NzMwMjk4LCJpYXQiOjE3MjU3MjY2OTgsImp0aSI6IjlmYWI4YTRkLTNlZWItNDQ1Yi05NzVkLTNhYjdlYmQ0MjM3NSIsInNjb3BlIjoiUk9MRV9BRE1JTiJ9.C_prq4ksV8kQZ7LbOnwopHph2e49v4eCQzogJPsPo6sZGIdPGCMNY13ri2GnKNoiEhPtS60dbwA2RZ0nJvbFmQ";
        doThrow(new AppException(ErrorCode.UNAUTHENTICATED))
                .when(invalidatedTokenRepository)
                .existsById(anyString());

        IntrospectRequest request = new IntrospectRequest(mockToken);
        IntrospectReponse response = authenticationService.introspect(request);

        assertFalse(response.isValid());
    }
}
