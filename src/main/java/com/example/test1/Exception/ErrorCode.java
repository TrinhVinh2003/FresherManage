package com.example.test1.Exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_EXISTED(1001, "user existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least 3 characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXIST(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    EMAIL_EXIST(1007, "Email existed", HttpStatus.BAD_REQUEST),
    PROJECT_NOT_EXIST(1008,"Project not exist",HttpStatus.NOT_FOUND),
    PROJECT_EXIST(1009,"Project existed",HttpStatus.BAD_REQUEST),
    CENTER_EXIST(1009,"Center existed",HttpStatus.BAD_REQUEST),
    CENTER_NOT_EXIST(1008,"Center not exist",HttpStatus.NOT_FOUND),
    SCORE_OF_FRESHER_EXIST(1009,"Fresher's score exist",HttpStatus.BAD_REQUEST),
    ASSIGNMENT_NOT_EXIST(1010,"Assigment not exist",HttpStatus.NOT_FOUND),
    FRESHER_NOT_EXIST(1011,"Fresher not exist",HttpStatus.NOT_FOUND),
    FRESHER_EXIST(1012,"Fresher existed",HttpStatus.BAD_REQUEST);
    private int code;
    private String message;
    private HttpStatus httpStatus;
}
