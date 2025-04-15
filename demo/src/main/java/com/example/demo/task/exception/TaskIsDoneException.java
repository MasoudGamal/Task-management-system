package com.example.demo.task.exception;

public class TaskIsDoneException extends RuntimeException {
    public TaskIsDoneException(String message) {
        super(message);
    }
}
