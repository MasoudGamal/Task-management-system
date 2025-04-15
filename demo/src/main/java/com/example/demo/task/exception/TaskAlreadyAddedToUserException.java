package com.example.demo.task.exception;

public class TaskAlreadyAddedToUserException extends RuntimeException {
    public TaskAlreadyAddedToUserException(String message) {
        super(message);
    }
}
