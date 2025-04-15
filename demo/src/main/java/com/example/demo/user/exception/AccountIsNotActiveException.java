package com.example.demo.user.exception;

public class AccountIsNotActiveException extends RuntimeException {
    public AccountIsNotActiveException(String message) {
        super(message);
    }
}
