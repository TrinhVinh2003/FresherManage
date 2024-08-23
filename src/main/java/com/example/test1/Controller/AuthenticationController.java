package com.example.test1.Controller;


import com.example.test1.Dto.request.ApiResponse;
import com.example.test1.Dto.request.AuthenticationRequest;
import com.example.test1.Dto.request.IntrospectRequest;
import com.example.test1.Dto.request.LogoutRequest;
import com.example.test1.Dto.response.AuthenticationResponse;
import com.example.test1.Dto.response.IntrospectReponse;
import com.example.test1.Service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> autheticate(@RequestBody AuthenticationRequest request){
        var result = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }
    @PostMapping("/introspect")
    ApiResponse<IntrospectReponse> autheticate(@RequestBody IntrospectRequest request) throws ParseException,JOSEException {
        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectReponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException,JOSEException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder()
                .build();
    }


}
