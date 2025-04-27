package com.funkybooboo.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "profiles")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "bio")
    private String bio;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "loyalty_points")
    private Integer loyaltyPoints;
}
