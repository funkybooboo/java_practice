package com.funkybooboo.store.repositories;

import com.funkybooboo.store.entities.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Custom implementation of the ProductCriteriaRepository interface.
 * This class uses the JPA Criteria API to dynamically build queries
 * based on provided filter parameters.
 */
@AllArgsConstructor
@Repository
public class ProductCriteriaRepositoryImpl implements ProductCriteriaRepository {

    // Injects the JPA EntityManager to manually build and run queries.
    @PersistenceContext
    private final EntityManager entityManager;

    /**
     * Finds products by optional criteria:
     * - partial name match (LIKE %name%)
     * - minimum price (>=)
     * - maximum price (<=)
     *
     * All conditions are optional. Only non-null parameters will be applied.
     */
    @Override
    public List<Product> findProductsByCriteria(String name, BigDecimal minPrice, BigDecimal maxPrice) {
        // 1. Obtain CriteriaBuilder from EntityManager
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        // 2. Create a CriteriaQuery for Product entity
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);

        // 3. Define the FROM clause (equivalent to "FROM Product p")
        Root<Product> root = cq.from(Product.class);

        // 4. Build a list of dynamic WHERE predicates
        List<Predicate> predicates = new ArrayList<>();

        if (name != null) {
            // Add condition: WHERE name LIKE %name%
            predicates.add(cb.like(root.get("name"), "%" + name + "%"));
        }

        if (minPrice != null) {
            // Add condition: WHERE price >= minPrice
            predicates.add(cb.greaterThanOrEqualTo(root.get("price"), minPrice));
        }

        if (maxPrice != null) {
            // Add condition: WHERE price <= maxPrice
            predicates.add(cb.lessThanOrEqualTo(root.get("price"), maxPrice));
        }

        // 5. Apply all predicates as the WHERE clause
        cq.select(root).where(predicates.toArray(new Predicate[0]));

        // 6. Execute the built query and return results
        return entityManager.createQuery(cq).getResultList();
    }
}
