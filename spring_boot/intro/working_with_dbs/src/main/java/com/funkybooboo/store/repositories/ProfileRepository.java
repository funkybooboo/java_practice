package com.funkybooboo.store.repositories;

import com.funkybooboo.store.entities.Profile;
import org.springframework.data.repository.CrudRepository;

/**
 * ProfileRepository is a Spring Data interface for performing CRUD operations
 * on the Profile entity.
 *
 * By extending CrudRepository<Profile, Long>, Spring automatically provides
 * implementations for basic operations like:
 *  - save(Profile entity)
 *  - findById(Long id)
 *  - findAll()
 *  - deleteById(Long id)
 *
 * You don’t need to write SQL or JPQL for those basic methods.
 */
public interface ProfileRepository extends CrudRepository<Profile, Long> {
    // You can define custom query methods here later, like:
    // Optional<Profile> findByUserId(Long userId);
}
