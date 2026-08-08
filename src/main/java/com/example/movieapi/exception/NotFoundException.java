package com.example.movieapi.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException (String resourceName ,Long id) {
        super(resourceName+ " with id " + id + " not found");
    }
}
