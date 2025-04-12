package com.funkybooboo.store.User;

import com.funkybooboo.store.Notification.NotificationService;

public class UserService {
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public UserService(UserRepository userRepository, NotificationService notificationService) {
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    public void registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists");
        }

        userRepository.save(user);
        notificationService.send("You registered successfully!", user.getEmail());
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
