package com.sobchenko.sneakershop.validator;

import com.sobchenko.sneakershop.model.User;

public final class UserValidator {
    private UserValidator() {
    }

    public static void checkEmail(User user) {
        if (!user.getEmail().matches(".*@.*\\..*")) {
            throw new IllegalArgumentException("Invalid email address");
        }
    }

}
