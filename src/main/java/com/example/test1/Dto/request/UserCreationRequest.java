package com.example.test1.Dto.request;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest  {
    @Size(min = 8 , message = "USERNAME_INVALID")  // role
    String username;
    @Size(min = 8  ,message = "INVALID_PASSWORD")
    String password;

    LocalDate dob;
}

