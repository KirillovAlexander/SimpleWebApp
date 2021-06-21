package com.alexkirillov.simplewebapp.dao;

import com.alexkirillov.simplewebapp.dto.Employee;

import java.util.List;

public interface EmployeeDAO {
    List<Employee> getAllEmployees();

    Employee getEmployee(int id);

    void saveEmployee(Employee employee);

    void deleteEmployee(int id);
}
