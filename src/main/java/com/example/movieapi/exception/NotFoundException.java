package com.example.movieapi.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException (String resourceName ,Long id) {
        super(resourceName+ " with id " + id + " not found");
    }

    public NotFoundException(String entity, String field, String value) {
        super(entity + " with " + field + " " + value + " not found");
    }
}
