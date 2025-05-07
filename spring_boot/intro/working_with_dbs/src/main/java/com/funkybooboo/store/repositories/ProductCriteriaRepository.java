package com.funkybooboo.store.repositories;

import com.funkybooboo.store.entities.Product;

import java.math.BigDecimal;
import java.util.List;

/**
 * ProductCriteriaRepository defines a custom repository interface for complex dynamic queries
 * that are difficult or awkward to express using Spring Data's derived query methods.
 *
 * This interface is **not automatically implemented** by Spring. Instead, you must create
 * a corresponding implementation class:
 *
 *   -> ProductCriteriaRepositoryImpl (note the 'Impl' suffix is required by Spring)
 *
 * That class manually uses the Criteria API to build queries at runtime based on input parameters.
 */
public interface ProductCriteriaRepository {

    /**
     * Dynamically finds products using optional filtering criteria:
     *
     * @param name      optional product name (partial match)
     * @param minPrice  optional minimum price
     * @param maxPrice  optional maximum price
     * @return list of matching Product entities
     *
     * - If all parameters are null, returns all products
     * - Uses CriteriaBuilder under the hood (see implementation class)
     */
    List<Product> findProductsByCriteria(String name, BigDecimal minPrice, BigDecimal maxPrice);
}
