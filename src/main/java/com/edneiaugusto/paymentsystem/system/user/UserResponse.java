package com.edneiaugusto.paymentsystem.system.user;

import java.util.UUID;

public record UserResponse(UUID id, String name,
                           String email, boolean enabled,
                           UserRole role) {

    public static UserResponse fromEntity(User user) {
        return new UserResponse(user.getId(), user.getName(),
                user.getEmail(), user.isEnabled(),
                user.getRole());
    }

}
