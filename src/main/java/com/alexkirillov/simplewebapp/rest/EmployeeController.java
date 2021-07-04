package com.alexkirillov.simplewebapp.rest;

import com.alexkirillov.simplewebapp.dto.Employee;
import com.alexkirillov.simplewebapp.dto.MessageDTO;
import com.alexkirillov.simplewebapp.service.EmployeeService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Validated
@OpenAPIDefinition(info = @Info(title = "Employees service API", version = "1.0", description = "Employees Information"),
        servers = @Server(url = "http://localhost:8080", description = "local server"))
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final static Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(summary = "Get all employees")
    @GetMapping
    public List<Employee> showAllEmployees() {
        logger.debug("showAllEmployees()");
        return employeeService.getAllEmployees();
    }

    @Operation(summary = "Get employee by id")
    @GetMapping("/{id}")
    public Employee getEmployee(@NotNull @Positive @PathVariable long id) {
        logger.debug("getEmployee(id) where id = {}", id);
        return employeeService.getEmployee(id);
    }

    @Operation(summary = "Create employee")
    @PostMapping
    public Employee addEmployee(@Valid @RequestBody Employee employee) {
        logger.debug("addEmployee(employee) where employee = {}", employee);
        return employeeService.addEmployee(employee);
    }

    @Operation(summary = "Update employee by id")
    @PutMapping("/{id}")
    public Employee updateEmployee(@NotNull @Positive @PathVariable long id, @RequestBody Employee employee) {
        logger.debug("updateEmployee(id, employee) where id = {} and employee = {}", id, employee);
        return employeeService.updateEmployee(id, employee);
    }

    @Operation(summary = "Delete employee")
    @DeleteMapping("/{id}")
    public MessageDTO deleteEmployee(@NotNull @Positive @PathVariable long id) {
        logger.debug("deleteEmployee(id) where id = {}", id);
        employeeService.deleteEmployee(id);
        return new MessageDTO("Employee with id = " + id + " was deleted");
    }

}
