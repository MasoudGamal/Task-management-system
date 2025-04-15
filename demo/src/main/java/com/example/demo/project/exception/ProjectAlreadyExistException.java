package com.example.demo.project.exception;

public class ProjectAlreadyExistException extends RuntimeException {
    public ProjectAlreadyExistException(String massage) {
        super(massage);
    }
}