package com.alexkirillov.simplewebapp.rest;

import com.alexkirillov.simplewebapp.dto.Employee;
import com.alexkirillov.simplewebapp.dto.MessageDTO;
import com.alexkirillov.simplewebapp.exception.NoSuchEmployeeException;
import com.alexkirillov.simplewebapp.exception.SQLInsertException;
import com.alexkirillov.simplewebapp.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Validated
@RestController("/api")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public List<Employee> showAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/employees/{id}")
    public Employee getEmployee(@NotNull @Positive @PathVariable int id) {
        return employeeService.getEmployee(id);
    }

    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee employee) {
        employeeService.addEmployee(employee);
        return employee;
    }

    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee employee) {
        employeeService.updateEmployee(employee);
        return employee;
    }

    @DeleteMapping("/employees/{id}")
    public MessageDTO deleteEmployee(@NotNull @Positive @PathVariable int id) {
        employeeService.deleteEmployee(id);
        return new MessageDTO("Employee with id = " + id + " was deleted");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoSuchEmployeeException.class)
    public MessageDTO employeeIncorrectData(NoSuchEmployeeException e) {
        return new MessageDTO(e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SQLInsertException.class)
    public MessageDTO employeeIncorrectData(SQLInsertException e) {
        return new MessageDTO(e.getMessage());
    }
}
