package com.funkybooboo.store.repositories;

import com.funkybooboo.store.entities.User;
import com.funkybooboo.store.projections.UserSummary;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * UserRepository is a Spring Data interface that provides basic CRUD methods
 * (save, findById, deleteById, etc.) for the User entity.
 * It also defines custom query methods using JPQL and projections.
 */
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Finds a User by their email and fetches the 'tags' and 'addresses' relationships eagerly.
     *
     * Normally, relationships like `@OneToMany` are loaded lazily, which can lead to
     * LazyInitializationExceptions if accessed outside a transaction.
     *
     * @EntityGraph overrides that by fetching specified fields in the same query.
     */
    @EntityGraph(attributePaths = {"tags", "addresses"})
    Optional<User> findByEmail(String email);

    /**
     * Retrieves all users and eagerly fetches the 'addresses' collection.
     *
     * Using @Query with @EntityGraph allows us to define both a custom JPQL query
     * and a fetch strategy override. This avoids the "N+1 select problem."
     */
    @EntityGraph(attributePaths = "addresses")
    @Query("select u from User u")
    List<User> findAllWithAddresses();

    /**
     * Returns a lightweight view (projection) of users who have more than a certain number
     * of loyalty points in their associated profile.
     *
     * Instead of returning full User entities, this method returns a custom interface projection
     * (UserSummary) to fetch only selected fields (id and email).
     *
     * `u.profile.loyaltyPoints` navigates a relation, so assumes a `@OneToOne` or similar
     * link between User and Profile.
     */
    @Query("select u.id as id, u.email as email from User u where u.profile.loyaltyPoints > ?1 order by u.email")
    List<UserSummary> findLoyalUsers(int loyaltyPoints);
}
