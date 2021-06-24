package com.alexkirillov.simplewebapp.dao;

import com.alexkirillov.simplewebapp.dto.Employee;
import com.alexkirillov.simplewebapp.exception.NoSuchEmployeeException;
import com.alexkirillov.simplewebapp.exception.SQLInsertException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {

    private final JdbcTemplate jdbcTemplate;

    public EmployeeDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    @Transactional
    public List<Employee> getAllEmployees() {
        return jdbcTemplate.query("SELECT * FROM Employee ORDER BY employee_id", new EmployeeMapper());
    }

    @Override
    @Transactional
    public Optional<Employee> getEmployee(int id) {
        return jdbcTemplate.query("SELECT * FROM Employee WHERE employee_id=?", new EmployeeMapper(), id)
                .stream().findAny();
    }

    @Override
    @Transactional
    public Employee addEmployee(Employee employee) {
        String sqlInsert = "INSERT INTO employee (first_name, last_name, department_id, job_title, gender) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = jdbcTemplate.getDataSource().getConnection().prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setInt(3, employee.getDepartmentId());
            statement.setString(4, employee.getJobTitle());
            statement.setString(5, employee.getGender().toString());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLInsertException("Creating user failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    employee.setId(generatedKeys.getInt(1));
                }
                else {
                    throw new SQLInsertException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return employee;
    }

    @Override
    @Transactional
    public Employee updateEmployee(Employee employee) {
        Optional<Employee> optional = jdbcTemplate.query("SELECT * FROM Employee WHERE employee_id=?", new EmployeeMapper(), employee.getId())
                .stream().findAny();
        if (optional.isEmpty())
            throw new NoSuchEmployeeException("Employee with id " + employee.getId() + " not founded.");
        jdbcTemplate.update("UPDATE employee SET first_name=?, last_name=?, department_id=?, job_title=?, gender=?" +
                        " WHERE employee_id=?",
                employee.getFirstName(), employee.getLastName(), employee.getDepartmentId(), employee.getJobTitle(), employee.getGender().toString(),
                employee.getId());
        return employee;
    }

    @Override
    @Transactional
    public void deleteEmployee(int id) {
        Optional<Employee> optional = jdbcTemplate.query("SELECT * FROM Employee WHERE employee_id=?", new EmployeeMapper(), id)
                .stream().findAny();
        if (optional.isEmpty()) throw new NoSuchEmployeeException("Employee with id " + id + " not founded.");
        jdbcTemplate.update("DELETE FROM employee WHERE employee_id=?", id);
    }
}
