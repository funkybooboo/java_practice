package com.funkybooboo.store.projections;

/**
 * ProductSummary is an interface-based projection for the Product entity.
 *
 * Instead of returning the entire Product object (which may include price, category, etc.),
 * this projection lets Spring Data return only the `id` and `name` fields.
 *
 * Spring will map the results of a JPQL or derived query to this projection
 * based on matching the method names to entity field names.
 *
 * Example usage:
 *   List<ProductSummary> findByPriceBetween(BigDecimal min, BigDecimal max);
 *
 * Requirements:
 * - The query must return fields named `id` and `name`
 * - The return type in your repository method must be `ProductSummary`
 */
public interface ProductSummary {
    String getId();   // Should be Long if Product.id is Long
    String getName();
}
