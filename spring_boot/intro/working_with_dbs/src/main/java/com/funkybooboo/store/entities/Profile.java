package com.funkybooboo.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity // Declares this class as a JPA entity
@Table(name = "profiles") // Maps this entity to the "profiles" table
public class Profile {

    // ---------- Fields ----------

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // Primary key — this will be shared with the User entity

    @Column(name = "bio")
    private String bio;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "loyalty_points")
    private Integer loyaltyPoints;

    /**
     * One-to-one relationship with the User entity.
     *
     * - `@OneToOne`: Each profile belongs to one user
     * - `fetch = FetchType.LAZY`: the user is not loaded unless explicitly accessed
     * - `@JoinColumn(name = "id")`: this profile's ID is also the foreign key to the User
     * - `@MapsId`: this tells JPA to use the same ID as the associated User — sharing the primary key
     *   This pattern is known as a *shared primary key* one-to-one relationship.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id") // Same column used for both PK and FK
    @MapsId
    @ToString.Exclude // Prevents infinite loop in toString()
    private User user;
}
