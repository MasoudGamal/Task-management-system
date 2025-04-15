package com.example.demo.user.exception;

public class InvalidTaskStatusUpdateException extends RuntimeException {
    public InvalidTaskStatusUpdateException(String message) {
        super(message);
    }
}
