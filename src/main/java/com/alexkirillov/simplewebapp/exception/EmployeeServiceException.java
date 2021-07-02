package com.alexkirillov.simplewebapp.exception;

public class EmployeeServiceException extends RuntimeException{
    public EmployeeServiceException(String message) {
        super(message);
    }
}
