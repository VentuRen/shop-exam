package com.test.interview.models;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;
    private String address;

    @Temporal(TemporalType.DATE)
    private Date birthDate;

    private Boolean isActive = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false) 
    private Role role = Role.USER;  // Agregar rol por defecto

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private PasswordRecovery passwordRecovery;
}

