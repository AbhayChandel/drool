package com.hexlindia.drool.user.exception;

public class UserAccountNotFoundException extends RuntimeException {
    public UserAccountNotFoundException(String message) {
        super(message);
    }
}
