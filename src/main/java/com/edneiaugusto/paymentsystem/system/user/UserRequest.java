package com.edneiaugusto.paymentsystem.system.user;

public record UserRequest(String name, String email, String password, UserRole role) {

    public User asEntitty() {
        return new User(null, name, email, password, role, null, false);
    }

}
