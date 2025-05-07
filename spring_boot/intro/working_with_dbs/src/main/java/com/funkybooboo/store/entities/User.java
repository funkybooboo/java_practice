package com.funkybooboo.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity // Marks this class as a JPA entity, mapped to a table in the database
@Table(name = "users") // Maps this entity to the "users" table
public class User {

    // ---------- Fields ----------

    @Id // Marks this as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrementing ID
    @Column(name = "id") // Maps to the "id" column in the table
    private Long id;

    @Column(name = "name") // Simple string field
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    // ---------- Relationships ----------

    /**
     * One user can have many addresses.
     * - `mappedBy = "user"`: the Address entity owns the relationship
     * - `cascade = {PERSIST, REMOVE}`: saving or deleting a user cascades to addresses
     * - `orphanRemoval = true`: if an address is removed from the list, it will be deleted from the DB
     * - `LAZY` loading: addresses are only fetched when explicitly accessed
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude // Prevents infinite loop when printing
    private List<Address> addresses = new ArrayList<>();

    /**
     * Many users can share many tags (many-to-many)
     * - Uses a join table named "user_tags"
     * - EAGER loading means tags are fetched with the user
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_tags",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @Builder.Default
    @ToString.Exclude
    private Set<Tag> tags = new HashSet<>();

    /**
     * One-to-one relationship with Profile
     * - Profile owns the relationship (mappedBy = "user")
     * - Cascade remove: deleting the user deletes the profile
     */
    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Profile profile;

    /**
     * Many users can have many products in their wishlist.
     * - Join table "wishlist" manages this relationship
     */
    @ManyToMany
    @JoinTable(
            name = "wishlist",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @ToString.Exclude
    private Set<Product> wishlist = new HashSet<>();

    // ---------- Convenience Methods ----------

    // Maintains bi-directional relationship between user and address
    public void addAddress(Address address) {
        addresses.add(address);
        address.setUser(this);
    }

    public void removeAddress(Address address) {
        addresses.remove(address);
        address.setUser(null);
    }

    // Bi-directional many-to-many with tags
    public void addTag(Tag tag) {
        this.getTags().add(tag);
        tag.getUsers().add(this);
    }

    public void removeTag(Tag tag) {
        this.getTags().remove(tag);
        tag.getUsers().remove(this);
    }

    // One-to-one management for profile
    public void addProfile(Profile profile) {
        this.setProfile(profile);
        profile.setUser(this);
    }

    public void removeProfile(Profile profile) {
        this.setProfile(null);
        profile.setUser(null);
    }

    // Adds a product to wishlist
    public void addWishlist(Product product) {
        wishlist.add(product);
    }
}
