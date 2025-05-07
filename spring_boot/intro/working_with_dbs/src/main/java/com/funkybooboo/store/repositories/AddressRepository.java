package com.funkybooboo.store.repositories;

import com.funkybooboo.store.entities.Address;
import org.springframework.data.repository.CrudRepository;

/**
 * AddressRepository provides basic CRUD operations for the Address entity.
 *
 * By extending CrudRepository<Address, Long>, Spring will auto-generate common
 * methods such as:
 *   - save(Address entity)
 *   - findById(Long id)
 *   - findAll()
 *   - deleteById(Long id)
 *
 * You don't need to write any implementation code — Spring Data JPA creates it at runtime.
 *
 * The second generic type (Long) must match the type of the @Id field in the Address entity.
 */
public interface AddressRepository extends CrudRepository<Address, Long> {
    // You can add custom query methods here later, e.g.:
    // List<Address> findByCity(String city);
    // List<Address> findByUserId(Long userId);
}
