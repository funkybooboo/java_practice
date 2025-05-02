package com.funkybooboo.store.repositories;

import com.funkybooboo.store.entities.Product;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    // Derived Query Methods
    
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
    
    // Numbers
    List<Product> findByPrice(BigDecimal price);
    // select * from products where price = ?
    List<Product> findByPriceGreaterThan(BigDecimal price);
    List<Product> findByPriceGreaterThanEqual(BigDecimal price);
    List<Product> findByPriceLessThan(BigDecimal price);
    List<Product> findByPriceLessThanEqual(BigDecimal price);
    List<Product> findByPriceBetween(BigDecimal min, BigDecimal max);

    // Null
    List<Product> findByDescriptionNull(String description);
    List<Product> findByDescriptionNotNull(String description);

    // Multiple conditions
    List<Product> findByDescriptionAndNullAndNameNull(String description, String name);
    
    // Sort (OrderBy)
    List<Product> findByNameOrderByNameAsc(String name);
    List<Product> findByNameOrderByPrice(String name);
    
    // Limit (Top/First)
    List<Product> findTop5ByNameOrderByPrice(String name);
    List<Product> findFirst5ByNameLikeOrderByPrice(String name);
    
}
