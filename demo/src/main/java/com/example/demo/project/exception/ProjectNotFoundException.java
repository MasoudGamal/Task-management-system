package com.example.demo.project.exception;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(String massage) {
        super(massage);
    }
}