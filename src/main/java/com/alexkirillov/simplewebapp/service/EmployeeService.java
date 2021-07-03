package com.alexkirillov.simplewebapp.service;

import com.alexkirillov.simplewebapp.dto.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();

    Employee addEmployee(Employee employee);

    Employee updateEmployee(long id, Employee employee);

    Employee getEmployee(long id);

    void deleteEmployee(long id);
}
