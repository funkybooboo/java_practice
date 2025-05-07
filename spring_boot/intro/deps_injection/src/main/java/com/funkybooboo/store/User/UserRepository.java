package com.funkybooboo.store.User;

// This interface defines the contract for user data storage and retrieval.
// It does not contain implementation—only method signatures that must be implemented by a class (like InMemoryUserRepository).
public interface UserRepository {

    // Save a user to the repository (e.g., a database or in-memory map)
    void save(User user);

    // Find and return a user by their email.
    // If no user is found, the method may return null (depending on the implementation).
    User findByEmail(String email);
}
