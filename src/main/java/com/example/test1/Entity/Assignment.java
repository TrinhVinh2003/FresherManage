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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Float score1;
    private Float score2;
    private Float score3;

    private Float result ;

    @OneToOne
    @JoinColumn(name = "fresher_id", nullable = false, referencedColumnName = "id")
    private Fresher fresher;

    @PrePersist
    @PreUpdate
    private void calculateResult() {
        if (score1 != null && score2 != null && score3 != null) {
            this.result = (score1 + score2 + score3) / 3;
        } else {
            this.result = null;
        }
    }
}
