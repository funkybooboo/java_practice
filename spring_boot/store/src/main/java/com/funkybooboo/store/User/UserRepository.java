package com.funkybooboo.store.User;

public interface UserRepository {
    void save(User user);
    User findByEmail(String email);
}
