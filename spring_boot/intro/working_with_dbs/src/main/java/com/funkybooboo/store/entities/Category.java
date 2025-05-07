package com.funkybooboo.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity // Marks this class as a JPA-managed entity
@Builder
@Table(name = "categories") // Maps this entity to the "categories" table
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    // ---------- Fields ----------

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment for Byte PK
    @Column(name = "id")
    private Byte id; // Byte used for compact storage, OK for small datasets (0-127)

    @Column(name = "name")
    private String name;

    /**
     * One-to-many relationship with Product:
     * - One category can have many products.
     * - `mappedBy = "category"` means the `Product` entity owns the relationship.
     * - `cascade = PERSIST`: if a new product is added and saved with this category,
     *   the category will persist (if not already).
     *
     * NOTE: Without `orphanRemoval = true`, removing a product from this set does NOT delete it from the database.
     */
    @OneToMany(mappedBy = "category", cascade = CascadeType.PERSIST)
    private Set<Product> products = new HashSet<>();

    /**
     * Convenience constructor for quick use when you only need to set the ID (e.g. for lookups).
     */
    public Category(byte id) {
        this.id = id;
    }
}
