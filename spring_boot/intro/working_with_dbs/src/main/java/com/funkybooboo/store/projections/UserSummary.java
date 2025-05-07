package com.funkybooboo.store.projections;

/**
 * UserSummary is an interface-based projection used to return
 * only selected fields of the User entity — in this case, `id` and `email`.
 *
 * Spring Data will automatically implement this interface and map the
 * results of a JPQL query to it, based on getter method names.
 *
 * This allows you to:
 *  - Avoid loading unnecessary data from the User entity
 *  - Improve performance by fetching only required columns
 *  - Keep the projection lightweight and simple
 *
 * Example usage in repository:
 *
 *   @Query("select u.id as id, u.email as email from User u where u.profile.loyaltyPoints > ?1 order by u.email")
 *   List<UserSummary> findLoyalUsers(int loyaltyPoints);
 */
public interface UserSummary {
    Long getId();
    String getEmail();
}
