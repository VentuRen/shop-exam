package com.test.interview.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "password_recovery")
@Data
public class PasswordRecovery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expiration;
}