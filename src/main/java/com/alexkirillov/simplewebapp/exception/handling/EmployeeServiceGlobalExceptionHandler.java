package com.alexkirillov.simplewebapp.exception.handling;

import com.alexkirillov.simplewebapp.dto.MessageDTO;
import com.alexkirillov.simplewebapp.exception.EmployeeServiceException;
import com.alexkirillov.simplewebapp.exception.EmployeeServiceNotFoundException;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@RestControllerAdvice
public class EmployeeServiceGlobalExceptionHandler {

    private final static Logger logger = Logger.getLogger(EmployeeServiceGlobalExceptionHandler.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EmployeeServiceNotFoundException.class)
    public MessageDTO employeeServiceNotFoundException(EmployeeServiceNotFoundException e) {
        logger.error("employeeServiceNotFoundException: " + getStackTraceMessage(e));
        return new MessageDTO(e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(EmployeeServiceException.class)
    public MessageDTO employeeServiceException(EmployeeServiceException e) {
        logger.error("employeeServiceException: " + getStackTraceMessage(e));
        return new MessageDTO(e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public MessageDTO throwableException(Throwable e) {
        logger.error("throwableException: " + getStackTraceMessage(e));
        return new MessageDTO(e.getMessage());
    }

    private String getStackTraceMessage(Throwable exception) {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(exception.getStackTrace()).forEachOrdered((item) -> {
            sb.append("\n");
            sb.append(item);
        });
        return sb.toString();
    }
}
