package com.funkybooboo.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity // Marks this class as a JPA entity mapped to a table
@Table(name = "tags") // Binds this entity to the "tags" table
public class Tag {

    // ---------- Fields ----------

    @Id // Marks this as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment strategy
    @Column(name = "id") // Maps to column "id" in the DB
    private Long id;

    @Column(name = "name") // Simple string column
    private String name;

    /**
     * Many-to-many relationship with User.
     * - `mappedBy = "tags"`: the User entity owns the relationship
     * - This side is the inverse (read-only side) of the mapping
     * - `@ToString.Exclude`: avoids recursion when printing
     */
    @ManyToMany(mappedBy = "tags")
    @ToString.Exclude
    private Set<User> users = new HashSet<>();

    /**
     * Convenience constructor for quick Tag creation.
     */
    public Tag(String name) {
        this.name = name;
    }
}
