package com.funkybooboo.store.entities;

import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
@Entity // Declares this class as a JPA entity
@Table(name = "addresses") // Maps to the "addresses" table in the database
public class Address {

    // ---------- Fields ----------

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremented ID
    @Column(name = "id")
    private Long id;

    @Column(name = "street") // Maps to column "street"
    private String street;

    @Column(name = "city") // Maps to column "city"
    private String city;

    @Column(name = "zip") // Maps to column "zip"
    private String zip;

    @Column(name = "state") // Maps to column "state"
    private String state;

    /**
     * Many-to-one relationship with User:
     * - Many addresses can belong to one user.
     * - `@JoinColumn(name = "user_id")` means the addresses table has a `user_id` FK column.
     * - `fetch = FetchType.LAZY`: the User is only fetched when needed.
     * - `@ToString.Exclude`: prevents infinite recursion when printing objects.
     *
     * This is the owning side of the relationship. The `User` entity has:
     *   @OneToMany(mappedBy = "user", ...)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;
}
