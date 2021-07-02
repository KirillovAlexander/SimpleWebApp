package com.alexkirillov.simplewebapp.service;

import com.alexkirillov.simplewebapp.dao.EmployeeDAO;
import com.alexkirillov.simplewebapp.exception.EmployeeServiceNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceImplTest {

    private static EmployeeServiceImpl employeeService;

    @BeforeAll
    public static void init() {
        EmployeeDAO employeeDAOMock = Mockito.mock(EmployeeDAO.class);
        Mockito.when(employeeDAOMock.getEmployee(0))
                .thenReturn(Optional.empty());
        employeeService = new EmployeeServiceImpl(employeeDAOMock);
    }

    @Test
    void getEmployee() {
        Throwable thrown = assertThrows(EmployeeServiceNotFoundException.class, () -> employeeService.getEmployee(0));
        assertNotNull(thrown.getMessage());
    }
}