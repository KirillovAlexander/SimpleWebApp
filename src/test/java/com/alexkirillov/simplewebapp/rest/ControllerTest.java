package com.alexkirillov.simplewebapp.rest;

import com.alexkirillov.simplewebapp.dto.Employee;
import com.alexkirillov.simplewebapp.dto.Gender;
import com.alexkirillov.simplewebapp.exception.EmployeeServiceNotFoundException;
import com.alexkirillov.simplewebapp.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class ControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    EmployeeService employeeService;

    private static Employee testEmployee = new Employee();
    private static Employee testEmployee1 = new Employee();

    static {
        testEmployee.setId(1);
        testEmployee.setFirstName("TestName");
        testEmployee.setLastName("TestLastName");
        testEmployee.setDepartmentId(1);
        testEmployee.setJobTitle("TestJobTitle");
        testEmployee.setGender(Gender.FEMALE);

        testEmployee1.setId(2);
        testEmployee1.setFirstName("TestName1");
        testEmployee1.setLastName("TestLastName1");
        testEmployee1.setDepartmentId(2);
        testEmployee1.setJobTitle("TestJobTitle1");
        testEmployee1.setGender(Gender.MALE);
    }

    @Test
    public void whenGetAllEmployees_thenStatusOkAndEmployeesReturned() throws Exception {
        List<Employee> employees = new ArrayList<>();
        employees.add(testEmployee);
        employees.add(testEmployee1);

        when(employeeService.getAllEmployees()).thenReturn(employees);

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)))

                .andExpect(jsonPath("$[0].id", is(testEmployee.getId())))
                .andExpect(jsonPath("$[0].firstName", is(testEmployee.getFirstName())))
                .andExpect(jsonPath("$[0].lastName", is(testEmployee.getLastName())))
                .andExpect(jsonPath("$[0].departmentId", is(testEmployee.getDepartmentId())))
                .andExpect(jsonPath("$[0].jobTitle", is(testEmployee.getJobTitle())))
                .andExpect(jsonPath("$[0].gender", is(testEmployee.getGender().toString())))

                .andExpect(jsonPath("$[1].id", is(testEmployee1.getId())))
                .andExpect(jsonPath("$[1].firstName", is(testEmployee1.getFirstName())))
                .andExpect(jsonPath("$[1].lastName", is(testEmployee1.getLastName())))
                .andExpect(jsonPath("$[1].departmentId", is(testEmployee1.getDepartmentId())))
                .andExpect(jsonPath("$[1].jobTitle", is(testEmployee1.getJobTitle())))
                .andExpect(jsonPath("$[1].gender", is(testEmployee1.getGender().toString())));
    }

    @Test
    public void givenEmployee_whenGetEmployeeByID_thenStatusOkAndEmployeeReturned() throws Exception {

        when(employeeService.getEmployee(testEmployee.getId())).thenReturn(testEmployee);
        mockMvc.perform(get("/employees/" + testEmployee.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id", is(testEmployee.getId())))
                .andExpect(jsonPath("$.firstName", is(testEmployee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(testEmployee.getLastName())))
                .andExpect(jsonPath("$.departmentId", is(testEmployee.getDepartmentId())))
                .andExpect(jsonPath("$.jobTitle", is(testEmployee.getJobTitle())))
                .andExpect(jsonPath("$.gender", is(testEmployee.getGender().toString())));
    }

    @Test
    public void whenGetEmployeeByNonExistentID_thenClientError() throws Exception {
        int is400 = 1;
        when(employeeService.getEmployee(is400)).thenThrow(new EmployeeServiceNotFoundException("No such employee exception"));
        mockMvc.perform(get("/employees/" + is400))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.message", is("No such employee exception")));

    }

    @Test
    public void givenEmployee_whenAdd_thenStatusOkAndEmployeeReturned() throws Exception {
        when(employeeService.addEmployee(testEmployee)).thenReturn(testEmployee);
        ObjectMapper mapper = new ObjectMapper();
        String testEmployeeAsJson = mapper.writeValueAsString(testEmployee);
        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testEmployeeAsJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id", is(testEmployee.getId())))
                .andExpect(jsonPath("$.firstName", is(testEmployee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(testEmployee.getLastName())))
                .andExpect(jsonPath("$.departmentId", is(testEmployee.getDepartmentId())))
                .andExpect(jsonPath("$.jobTitle", is(testEmployee.getJobTitle())))
                .andExpect(jsonPath("$.gender", is(testEmployee.getGender().toString())));
    }

    @Test
    public void givenEmployee_whenUpdate_thenStatusOkAndEmployeeReturned() throws Exception {
        when(employeeService.updateEmployee(testEmployee)).thenReturn(testEmployee);
        ObjectMapper mapper = new ObjectMapper();
        String testEmployeeAsJson = mapper.writeValueAsString(testEmployee);
        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testEmployeeAsJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id", is(testEmployee.getId())))
                .andExpect(jsonPath("$.firstName", is(testEmployee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(testEmployee.getLastName())))
                .andExpect(jsonPath("$.departmentId", is(testEmployee.getDepartmentId())))
                .andExpect(jsonPath("$.jobTitle", is(testEmployee.getJobTitle())))
                .andExpect(jsonPath("$.gender", is(testEmployee.getGender().toString())));
    }
}
