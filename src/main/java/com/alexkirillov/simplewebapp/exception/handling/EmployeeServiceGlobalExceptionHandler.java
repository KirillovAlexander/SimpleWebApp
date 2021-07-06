package com.alexkirillov.simplewebapp.exception.handling;

import com.alexkirillov.simplewebapp.dto.MessageDTO;
import com.alexkirillov.simplewebapp.exception.EmployeeServiceException;
import com.alexkirillov.simplewebapp.exception.EmployeeServiceNotFoundException;
import io.swagger.v3.oas.annotations.Hidden;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EmployeeServiceGlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceGlobalExceptionHandler.class);


    @Hidden
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EmployeeServiceNotFoundException.class)
    public MessageDTO employeeServiceNotFoundException(EmployeeServiceNotFoundException e) {
        logger.error("employeeServiceNotFoundException: ", e);
        return new MessageDTO(e.getMessage());
    }

    @Hidden
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(EmployeeServiceException.class)
    public MessageDTO employeeServiceException(EmployeeServiceException e) {
        logger.error("employeeServiceException: ", e);
        return new MessageDTO(e.getMessage());
    }

//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(Throwable.class)
//    public MessageDTO throwableException(Throwable e) {
//        logger.error("throwableException: ", e);
//        return new MessageDTO(e.getMessage());
//    }
}
