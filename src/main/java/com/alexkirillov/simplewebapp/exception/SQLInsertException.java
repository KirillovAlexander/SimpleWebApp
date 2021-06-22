package com.alexkirillov.simplewebapp.exception;

public class SQLInsertException extends RuntimeException{
    public SQLInsertException(String message) {
        super(message);
    }
}
