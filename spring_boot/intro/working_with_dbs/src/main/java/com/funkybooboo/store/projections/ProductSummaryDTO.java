package com.funkybooboo.store.projections;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ProductSummaryDTO is a simple Data Transfer Object (DTO) used to return
 * a lightweight projection of a Product entity.
 *
 * Instead of returning the full Product object (with possibly many fields and relations),
 * we only return a few selected fields — in this case, `id` and `name`.
 *
 * This improves performance and bandwidth usage when only a summary is needed.
 *
 * Used with a constructor expression in JPQL:
 *   @Query("select new com.funkybooboo.store.projections.ProductSummaryDTO(p.id, p.name) from Product p where p.category = ?1")
 */
@Getter // Lombok: automatically generates getters for both fields
@AllArgsConstructor // Lombok: generates a constructor with all fields as arguments
public class ProductSummaryDTO {
    private Long id;
    private String name;
}
