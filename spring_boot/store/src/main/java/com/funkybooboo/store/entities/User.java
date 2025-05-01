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
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;
    
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private List<Address> addresses = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "user_tags",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @Builder.Default
    @ToString.Exclude
    private Set<Tag> tags = new HashSet<>();
    
    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Profile profile;
    
    @ManyToMany
    @JoinTable(
        name = "wishlist",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @ToString.Exclude
    private Set<Product> wishlist = new HashSet<>();
    
    public void addAddress(Address address) {
        addresses.add(address);
        address.setUser(this);
    }
    
    public void removeAddress(Address address) {
        addresses.remove(address);
        address.setUser(null);
    }
    
    public void addTag(Tag tag) {
        this.getTags().add(tag);
        tag.getUsers().add(this);
    }

    public void removeTag(Tag tag) {
        this.getTags().remove(tag);
        tag.getUsers().remove(this);
    }
    
    public void addProfile(Profile profile) {
        this.setProfile(profile);
        profile.setUser(this);
    }

    public void removeProfile(Profile profile) {
        this.setProfile(null);
        profile.setUser(null);
    }

    public void addWishlist(Product product) {
        wishlist.add(product);
    }
}
