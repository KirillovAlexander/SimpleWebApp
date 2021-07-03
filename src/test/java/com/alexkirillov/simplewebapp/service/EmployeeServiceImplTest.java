package com.alexkirillov.simplewebapp.service;

import com.alexkirillov.simplewebapp.dao.EmployeeRepository;
import com.alexkirillov.simplewebapp.exception.EmployeeServiceNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmployeeServiceImplTest {

    private static EmployeeServiceImpl employeeService;

    @BeforeAll
    public static void init() {
        EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
        Mockito.when(employeeRepository.findById(0L))
                .thenReturn(Optional.empty());
        employeeService = new EmployeeServiceImpl(employeeRepository);
    }

    @Test
    void getEmployee() {
        Throwable thrown = assertThrows(EmployeeServiceNotFoundException.class, () -> employeeService.getEmployee(0L));
        assertNotNull(thrown.getMessage());
    }
}