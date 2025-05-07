package com.funkybooboo.store.User;

import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;

// Marks this class as a Repository component in Spring.
// It represents the data access layer, and Spring can use this for dependency injection.
@Repository
public class InMemoryUserRepository implements UserRepository {

    // A simple in-memory map to store users by their email.
    // This simulates a database for learning/testing purposes.
    private final Map<String, User> users = new HashMap<>();

    // Implements the save method defined in the UserRepository interface.
    // Stores the user in the map using the user's email as the key.
    @Override
    public void save(User user) {
        users.put(user.getEmail(), user);
    }

    // Implements the findByEmail method from the interface.
    // Looks up a user by email. Returns null if not found.
    @Override
    public User findByEmail(String email) {
        return users.getOrDefault(email, null);
    }
}
