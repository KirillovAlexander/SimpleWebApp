package com.alexkirillov.simplewebapp.exception;

public class EmployeeServiceNotFoundException extends RuntimeException {
    public EmployeeServiceNotFoundException(String message) {
        super(message);
    }
}
