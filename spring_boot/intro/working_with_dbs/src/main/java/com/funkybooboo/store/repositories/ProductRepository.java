package com.funkybooboo.store.repositories;

import com.funkybooboo.store.entities.Category;
import com.funkybooboo.store.entities.Product;
import com.funkybooboo.store.projections.ProductSummaryDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * ProductRepository handles all data operations related to Product entities.
 *
 * - JpaRepository<Product, Long>: gives full CRUD + pagination + sorting
 * - ProductCriteriaRepository: for custom Criteria API queries
 * - JpaSpecificationExecutor<Product>: enables use of Specification<T> for flexible filtering
 */
public interface ProductRepository extends JpaRepository<Product, Long>,
        ProductCriteriaRepository,
        JpaSpecificationExecutor<Product> {

    // ---------- Derived Query Methods ----------

    // Finds all products with exact name match
    List<Product> findByName(String name);

    // Finds all products where name LIKE ? (wildcards required manually)
    List<Product> findByNameLike(String name);

    // Finds products where name NOT LIKE ?
    List<Product> findByNameNotLike(String name);

    // These use automatic wildcards:
    List<Product> findByNameContains(String name);    // name LIKE %name%
    List<Product> findByNameStartsWith(String name);  // name LIKE name%
    List<Product> findByNameEndsWith(String name);    // name LIKE %name

    // Price-based filters
    List<Product> findByPrice(BigDecimal price);
    List<Product> findByPriceGreaterThan(BigDecimal price);
    List<Product> findByPriceGreaterThanEqual(BigDecimal price);
    List<Product> findByPriceLessThan(BigDecimal price);
    List<Product> findByPriceLessThanEqual(BigDecimal price);
    List<Product> findByPriceBetween(BigDecimal min, BigDecimal max);

    // Match on both fields
    List<Product> findByDescription(String description);
    List<Product> findByDescriptionAndName(String description, String name);

    // ---------- Sorting ----------

    // Sorts result by name ascending
    List<Product> findByNameOrderByNameAsc(String name);

    // Sorts result by price (default ASC)
    List<Product> findByNameOrderByPrice(String name);

    // ---------- Limiting ----------

    // Limits result to top 5 by price for given name
    List<Product> findTop5ByNameOrderByPrice(String name);

    // Uses LIKE + ORDER BY price and limits to first 5 results
    List<Product> findFirst5ByNameLikeOrderByPrice(String name);

    // ---------- Stored Procedure ----------

    // Calls a stored procedure named `findProductsByPrice`
    @Procedure("findProductsByPrice")
    List<Product> findProducts(BigDecimal min, BigDecimal max);

    // ---------- Custom Sorting & Filtering ----------

    // Derived method with sort by name
    List<Product> findByPriceBetweenOrderByName(BigDecimal min, BigDecimal max);

    // Native SQL version of the same
    @Query(value = "select * from products p where p.price between :min and :max order by p.name", nativeQuery = true)
    List<Product> findProductsSql(@Param("min") BigDecimal min, @Param("max") BigDecimal max);

    // JPQL version (uses entity and field names)
    @Query("select p from Product p where p.price between :min and :max order by p.name")
    List<Product> findProductsJPQL(@Param("min") BigDecimal min, @Param("max") BigDecimal max);

    // ---------- Aggregation ----------

    // Count how many products fall in a price range
    @Query("select count(*) from Product p where p.price between :min and :max")
    long countProducts(@Param("min") BigDecimal min, @Param("max") BigDecimal max);

    // ---------- Custom Update (must be used in a @Transactional method) ----------

    // Update all products in a category to a new price
    @Modifying
    @Query("update Product p set p.price = :newPrice where p.category.id = :categoryId")
    void updatePriceByCategory(@Param("newPrice") BigDecimal newPrice,
                               @Param("categoryId") Byte categoryId);

    // ---------- Projection (return only selected fields into a DTO) ----------

    // Creates a ProductSummaryDTO using JPQL constructor expression
    @Query("select new com.funkybooboo.store.projections.ProductSummaryDTO(p.id, p.name) from Product p where p.category = ?1")
    List<ProductSummaryDTO> findByCategory(Category category);
}
