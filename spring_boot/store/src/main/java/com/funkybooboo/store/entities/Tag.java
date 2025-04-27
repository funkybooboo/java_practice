package com.funkybooboo.store.entities;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;
}
