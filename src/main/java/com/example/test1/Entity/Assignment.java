package com.example.test1.Entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Assignment {
    @Id


    private Float score1;
    private Float score2;
    private Float score3;

    private Float result;

    @OneToOne
    @JoinColumn(name = "fresher_id", nullable = false, referencedColumnName = "id")
    private Fresher fresher;

    @PrePersist
    @PreUpdate
    private void calculateResult() {
        this.result = (score1 + score2 + score3) / 3;
    }
}
