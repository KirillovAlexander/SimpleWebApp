package com.alexkirillov.simplewebapp.rest;

import com.alexkirillov.simplewebapp.dto.Employee;
import com.alexkirillov.simplewebapp.dto.MessageDTO;
import com.alexkirillov.simplewebapp.service.EmployeeService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Array of employees",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Employee.class)))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageDTO.class))})
    })
    @GetMapping
    public List<Employee> showAllEmployees() {
        logger.debug("showAllEmployees()");
        return employeeService.getAllEmployees();
    }

    @Operation(summary = "Get an employee by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the employee",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class))}),
            @ApiResponse(responseCode = "404", description = "Employee not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageDTO.class))})
    })
    @GetMapping("/{id}")
    public Employee getEmployee(@NotNull @Positive @PathVariable long id) {
        logger.debug("getEmployee(id) where id = {}", id);
        return employeeService.getEmployee(id);
    }

    @Operation(summary = "Create employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created employee",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageDTO.class))})
    })
    @PostMapping
    public Employee addEmployee(@Valid @RequestBody Employee employee) {
        logger.debug("addEmployee(employee) where employee = {}", employee);
        return employeeService.addEmployee(employee);
    }

    @Operation(summary = "Update an employee by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated employee",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Employee.class))}),
            @ApiResponse(responseCode = "404", description = "Employee not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageDTO.class))})
    })
    @PutMapping("/{id}")
    public Employee updateEmployee(@NotNull @Positive @PathVariable long id, @RequestBody Employee employee) {
        logger.debug("updateEmployee(id, employee) where id = {} and employee = {}", id, employee);
        return employeeService.updateEmployee(id, employee);
    }

    @Operation(summary = "Delete employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Confirmation message",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Employee not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageDTO.class))})
    })
    @DeleteMapping("/{id}")
    public MessageDTO deleteEmployee(@NotNull @Positive @PathVariable long id) {
        logger.debug("deleteEmployee(id) where id = {}", id);
        employeeService.deleteEmployee(id);
        return new MessageDTO("Employee with id = " + id + " was deleted");
    }
}
