package com.funkybooboo.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@Entity // Marks this class as a JPA entity
@Table(name = "products") // Maps this entity to the "products" table
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    // ---------- Fields ----------

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrementing ID
    @Column(name = "id")
    private Long id;

    @Column(name = "name") // Maps to "name" column
    private String name;

    @Column(name = "description") // Maps to "description" column
    private String description;

    @Column(name = "price") // Maps to "price" column
    private BigDecimal price;

    /**
     * Many-to-one relationship between Product and Category.
     *
     * - Many products can belong to one category.
     * - `@JoinColumn(name = "category_id")` creates a foreign key column in the products table.
     * - `cascade = PERSIST` means if a new Category is set and doesn't exist yet, it will be saved automatically when the Product is saved.
     * - Note: No `fetch` specified — defaults to `EAGER` for `@ManyToOne`.
     */
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id") // FK to the Category table
    private Category category;
}
