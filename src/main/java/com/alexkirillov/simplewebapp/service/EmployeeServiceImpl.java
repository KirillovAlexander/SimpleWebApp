package com.alexkirillov.simplewebapp.service;

import com.alexkirillov.simplewebapp.dao.EmployeeDAO;
import com.alexkirillov.simplewebapp.dto.Employee;
import com.alexkirillov.simplewebapp.exception.NoSuchEmployeeException;
import com.alexkirillov.simplewebapp.exception.handling.EmployeeGlobalExceptionHandler;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final static Logger logger = Logger.getLogger(EmployeeGlobalExceptionHandler.class);
    private final EmployeeDAO employeeDAO;

    public EmployeeServiceImpl(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @Override
    public List<Employee> getAllEmployees() {
        logger.info("EmployeeServiceImpl - getAllEmployees()");
        return employeeDAO.getAllEmployees();
    }

    @Override
    public Employee getEmployee(int id) {
        logger.info("EmployeeServiceImpl - getEmployee(id) with id = " + id);
        Optional<Employee> optional = employeeDAO.getEmployee(id);
        return optional.orElseThrow(() -> new NoSuchEmployeeException("Employee with id " + id + " not founded."));
    }

    @Override
    public Employee addEmployee(Employee employee) {
        logger.info("EmployeeServiceImpl - addEmployee(employee) with employee = " + employee);
        return employeeDAO.addEmployee(employee);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        logger.info("EmployeeServiceImpl - updateEmployee(employee) with employee = " + employee);
        return employeeDAO.updateEmployee(employee);
    }

    @Override
    public void deleteEmployee(int id) {
        logger.info("EmployeeServiceImpl - deleteEmployee(id) with id = " + id);
        employeeDAO.deleteEmployee(id);
    }
}
