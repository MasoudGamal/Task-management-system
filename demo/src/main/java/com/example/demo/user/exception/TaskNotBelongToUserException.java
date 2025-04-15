package com.example.demo.user.exception;

public class TaskNotBelongToUserException extends RuntimeException {
    public TaskNotBelongToUserException(String message) {
        super(message);
    }
}
