package com.funkybooboo.store.repositories;

import com.funkybooboo.store.projections.ProductSummary;
import com.funkybooboo.store.entities.Category;
import com.funkybooboo.store.entities.Product;
import com.funkybooboo.store.projections.ProductSummaryDTO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    
    // String
    List<Product> findByName(String name);
    // select * from products where name = ?
    List<Product> findByNameLike(String name);
    // select * from products where name like ?
    List<Product> findByNameNotLike(String name);
    // select * from products where not name like ?
    List<Product> findByNameContains(String name);
    List<Product> findByNameStartsWith(String name);
    List<Product> findByNameEndsWith(String name);
    
    List<Product> findByPrice(BigDecimal price);
    // select * from products where price = ?
    List<Product> findByPriceGreaterThan(BigDecimal price);
    List<Product> findByPriceGreaterThanEqual(BigDecimal price);
    List<Product> findByPriceLessThan(BigDecimal price);
    List<Product> findByPriceLessThanEqual(BigDecimal price);
    List<Product> findByPriceBetween(BigDecimal min, BigDecimal max);

    List<Product> findByDescription(String description);

    List<Product> findByDescriptionAndName(String description, String name);
    
    // Sort (OrderBy)
    List<Product> findByNameOrderByNameAsc(String name);
    List<Product> findByNameOrderByPrice(String name);
    
    // Limit (Top/First)
    List<Product> findTop5ByNameOrderByPrice(String name);
    List<Product> findFirst5ByNameLikeOrderByPrice(String name);
    
    // Find products whose price are in a given range and sort by name
    List<Product> findByPriceBetweenOrderByName(BigDecimal min, BigDecimal max);
    @Query(value = "select * from products p where p.price between :min and :max order by p.name", nativeQuery = true)
    List<Product> findProductsSql(@Param("min") BigDecimal min, @Param("max") BigDecimal max);
    @Query("select p from Product p where p.price between :min and :max order by p.name")
    List<Product> findProductsJPQL(@Param("min") BigDecimal min, @Param("max") BigDecimal max);
    
    @Query("select count(*) from Product p where p.price between :min and :max")
    long countProducts(@Param("min") BigDecimal min, @Param("max") BigDecimal max);
    
    @Modifying
    @Query("update Product p set p.price = :newPrice where p.category.id = :categoryId")
    void updatePriceByCategory(@Param("newPrice") BigDecimal newPrice, @Param("categoryId") Byte categoryId); // must wrap in a method with @Transactional
    
    @Query("select new com.funkybooboo.store.projections.ProductSummaryDTO(p.id, p.name) from Product p where p.category = ?1")
    List<ProductSummaryDTO> findByCategory(Category category);
}
