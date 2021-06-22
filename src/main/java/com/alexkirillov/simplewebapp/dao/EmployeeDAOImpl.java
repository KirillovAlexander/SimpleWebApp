package com.alexkirillov.simplewebapp.dao;

import com.alexkirillov.simplewebapp.dto.Employee;
import com.alexkirillov.simplewebapp.exception_handling.NoSuchEmployeeException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {

    private final JdbcTemplate jdbcTemplate;

    public EmployeeDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Employee> getAllEmployees() {
        return jdbcTemplate.query("SELECT * FROM Employee", new EmployeeMapper());
    }

    @Override
    public Optional<Employee> getEmployee(int id) {
        return jdbcTemplate.query("SELECT * FROM Employee WHERE employee_id=?", new EmployeeMapper(), id)
                .stream().findAny();
    }

    @Override
    public Employee addEmployee(Employee employee) {
            jdbcTemplate.update("INSERT INTO employee (first_name, last_name, department_id, job_title, gender) VALUES (?, ?, ?, ?, ?)",
                    employee.getFirstName(), employee.getLastName(), employee.getDepartmentId(), employee.getJobTitle(), employee.getGender().toString());
            return employee;
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        Optional<Employee> optional = jdbcTemplate.query("SELECT * FROM Employee WHERE employee_id=?", new EmployeeMapper(), employee.getId())
                .stream().findAny();
        if (optional.isEmpty()) throw new NoSuchEmployeeException("Employee with id " + employee.getId() + " not founded.");
        jdbcTemplate.update("UPDATE employee SET first_name=?, last_name=?, department_id=?, job_title=?, gender=?" +
                        " WHERE employee_id=?",
                employee.getFirstName(), employee.getLastName(), employee.getDepartmentId(), employee.getJobTitle(), employee.getGender().toString(),
                employee.getId());
        return employee;
    }

    @Override
    public void deleteEmployee(int id) {
        Optional<Employee> optional = jdbcTemplate.query("SELECT * FROM Employee WHERE employee_id=?", new EmployeeMapper(), id)
                .stream().findAny();
        if (optional.isEmpty()) throw new NoSuchEmployeeException("Employee with id " + id + " not founded.");
        jdbcTemplate.update("DELETE FROM employee WHERE employee_id=?", id);
    }
}
