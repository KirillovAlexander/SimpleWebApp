package com.alexkirillov.simplewebapp.dao;

import com.alexkirillov.simplewebapp.dto.Employee;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public Employee getEmployee(int id) {
        return jdbcTemplate.query("SELECT * FROM Employee WHERE employee_id =?", new EmployeeMapper(), id)
                .stream().findAny().orElse(null);
    }

    @Override
    public void saveEmployee(Employee employee) {
        if (employee.getId() == 0) {
            jdbcTemplate.update("INSERT INTO employee (first_name, last_name, department_id, job_title, gender) VALUES (?, ?, ?, ?, ?)",
                    employee.getFirstName(), employee.getLastName(), employee.getDepartmentId(), employee.getJobTitle(), employee.getGender());
        } else {
            jdbcTemplate.update("UPDATE employee SET first_name=?, last_name=?, department_id=?, job_title=?, gender=?" +
                            " WHERE employee_id=?",
                    employee.getFirstName(), employee.getLastName(), employee.getDepartmentId(), employee.getJobTitle(), employee.getGender().toString(),
                    employee.getId());
        }
    }

    @Override
    public void deleteEmployee(int id) {
        jdbcTemplate.update("DELETE FROM employee WHERE employee_id=?", id);
    }
}
