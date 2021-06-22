package com.alexkirillov.simplewebapp.dao;

import com.alexkirillov.simplewebapp.dto.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeDAO {
    List<Employee> getAllEmployees();

    Optional<Employee> getEmployee(int id);

    Employee addEmployee(Employee employee);

    Employee updateEmployee(Employee employee);

    void deleteEmployee(int id);
}
