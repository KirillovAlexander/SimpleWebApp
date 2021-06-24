package com.alexkirillov.simplewebapp.exception.handling;

import com.alexkirillov.simplewebapp.dto.MessageDTO;
import com.alexkirillov.simplewebapp.exception.NoSuchEmployeeException;
import com.alexkirillov.simplewebapp.exception.SQLInsertException;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class EmployeeGlobalExceptionHandler {

    private final static Logger logger = Logger.getLogger(EmployeeGlobalExceptionHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoSuchEmployeeException.class)
    public MessageDTO employeeIncorrectData(NoSuchEmployeeException e) {
        logger.error("EmployeeGlobalExceptionHandler: " + e.getMessage());
        return new MessageDTO(e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SQLInsertException.class)
    public MessageDTO insertSQLException(SQLInsertException e) {
        logger.error("EmployeeGlobalExceptionHandler: " + e.getMessage());
        return new MessageDTO(e.getMessage());
    }
}
