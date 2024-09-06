package com.example.FresherManage.Dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Size;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 4, message = "USERNAME_INVALID") // role
    String username;

    @Size(min = 8, message = "INVALID_PASSWORD")
    String password;

    LocalDate dob;
}
