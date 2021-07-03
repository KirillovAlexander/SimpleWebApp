package com.alexkirillov.simplewebapp.service;

import com.alexkirillov.simplewebapp.dao.EmployeeRepository;
import com.alexkirillov.simplewebapp.dto.Employee;
import com.alexkirillov.simplewebapp.exception.EmployeeServiceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final static Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getAllEmployees() {
        logger.info("getAllEmployees()");
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployee(long id) {
        logger.info("getEmployee(id) with id = " + id);
        Optional<Employee> optional = employeeRepository.findById(id);
        return optional.orElseThrow(() -> new EmployeeServiceNotFoundException("Employee with id " + id + " not founded."));
    }

    @Override
    public Employee addEmployee(Employee employee) {
        logger.info("addEmployee(employee) with employee = " + employee);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(long id, Employee employee) {
        employee.setId(id);
        logger.info("updateEmployee(employee) with employee = " + employee);
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(long id) {
        logger.info("deleteEmployee(id) with id = " + id);
        employeeRepository.deleteById(id);
    }
}
