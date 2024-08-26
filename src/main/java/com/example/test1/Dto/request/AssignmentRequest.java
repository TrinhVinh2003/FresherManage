package com.example.test1.Dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssignmentRequest {
    private Float score1;
    private Float score2;
    private Float score3;
    //    @NotNull(message = "fresher is not null")
    private Long fresher_id;
}
