package com.example.movieapi.exception;

public class AlreadyExistsException extends RuntimeException {

    public AlreadyExistsException(String resource, String field, String value) {
        super(resource + " with " + field + " '" + value + "' already exists");
    }

    public AlreadyExistsException(String resource, String field) {
        super(resource + " with " + field + " not found");
    }
}
