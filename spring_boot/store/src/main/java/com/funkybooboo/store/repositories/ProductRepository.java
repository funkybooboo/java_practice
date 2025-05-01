package com.funkybooboo.store.repositories;

import com.funkybooboo.store.entities.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
