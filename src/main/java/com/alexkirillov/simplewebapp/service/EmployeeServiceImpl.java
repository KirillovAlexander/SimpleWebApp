package com.alexkirillov.simplewebapp.service;

import com.alexkirillov.simplewebapp.dao.EmployeeDAO;
import com.alexkirillov.simplewebapp.dto.Employee;
import com.alexkirillov.simplewebapp.exception.NoSuchEmployeeException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDAO employeeDAO;

    public EmployeeServiceImpl(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeDAO.getAllEmployees();
    }

    @Override
    public Employee getEmployee(int id) {
        Optional<Employee> optional = employeeDAO.getEmployee(id);
        return optional.orElseThrow(() -> new NoSuchEmployeeException("Employee with id " + id + " not founded."));
    }

    @Override
    public Employee addEmployee(Employee employee) {
        return employeeDAO.addEmployee(employee);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return employeeDAO.updateEmployee(employee);
    }

    @Override
    public void deleteEmployee(int id) {
        employeeDAO.deleteEmployee(id);
    }
}
