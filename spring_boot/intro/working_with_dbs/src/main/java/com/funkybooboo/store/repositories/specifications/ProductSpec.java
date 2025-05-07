package com.funkybooboo.store.repositories.specifications;

import com.funkybooboo.store.entities.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

/**
 * ProductSpec defines reusable Specification<Product> objects for building
 * dynamic and type-safe WHERE clauses in JPA queries.
 *
 * Specifications can be combined using .and() / .or() to construct
 * flexible and readable queries without manually writing JPQL or Criteria code.
 */
public class ProductSpec {

    /**
     * Creates a Specification that matches products whose name contains the given string.
     * Equivalent to: WHERE name LIKE %name%
     *
     * @param name partial name to search for
     * @return Specification<Product>
     */
    public static Specification<Product> hasName(String name) {
        return (root, query, cb) -> cb.like(root.get("name"), "%" + name + "%");
    }

    /**
     * Creates a Specification that matches products with a price >= given value.
     *
     * @param price minimum price
     * @return Specification<Product>
     */
    public static Specification<Product> hasPriceGreaterThanOrEqualTo(BigDecimal price) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("price"), price);
    }

    /**
     * Creates a Specification that matches products with a price <= given value.
     *
     * @param price maximum price
     * @return Specification<Product>
     */
    public static Specification<Product> hasPriceLessThanOrEqualTo(BigDecimal price) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("price"), price);
    }
}
