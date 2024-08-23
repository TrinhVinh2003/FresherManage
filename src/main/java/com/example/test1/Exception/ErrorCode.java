package com.example.test1.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_EXISTED(1001,"user existed",HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least 3 characters",HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least 8 characters",HttpStatus.BAD_REQUEST),
    USER_NOT_EXIST(1005,"User not existed",HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006,"Unauthenticated",HttpStatus.UNAUTHORIZED);

    private int code;
    private String message;
    private HttpStatus httpStatus;


}
