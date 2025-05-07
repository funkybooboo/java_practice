package com.funkybooboo.store.repositories;

import com.funkybooboo.store.entities.Category;
import org.springframework.data.repository.CrudRepository;

/**
 * CategoryRepository provides basic CRUD operations for the Category entity.
 *
 * By extending CrudRepository<Category, Byte>, Spring Data will automatically generate
 * implementations for common operations like:
 *   - save(Category category)
 *   - findById(Byte id)
 *   - findAll()
 *   - deleteById(Byte id)
 *   - existsById(Byte id)
 *
 * Note:
 *   - The second generic type (Byte) refers to the ID type of the Category entity.
 *   - Spring will generate the implementation at runtime — no need to write SQL or JPQL manually.
 */
public interface CategoryRepository extends CrudRepository<Category, Byte> {
    // You can add custom query methods later, e.g.:
    // List<Category> findByName(String name);
}
