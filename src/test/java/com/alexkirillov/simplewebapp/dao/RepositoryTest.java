package com.alexkirillov.simplewebapp.dao;

import com.alexkirillov.simplewebapp.dto.Employee;
import com.alexkirillov.simplewebapp.dto.Gender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.annotation.AfterTestMethod;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/*
    Tests use data from test/resources/db/changelog/v-1.1/10-insert-employee-data.sql
 */

@SpringBootTest
@ContextConfiguration(initializers = {RepositoryTest.Initializer.class})
@Testcontainers
public class RepositoryTest {

    @Autowired
    EmployeeDAO employeeDAO;

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("mydb")
            .withUsername("myuser")
            .withPassword("mypass");

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword(),
                    "spring.liquibase.enabled=true",
                    "spring.liquibase.change-log=classpath:db/changelog/db.changelog-test.xml"
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Test
    @Transactional
    public void givenID_whenGetEmployee_thenEmployeeReturned() {
        Optional<Employee> optional = employeeDAO.getEmployee(1);
        assertThat(optional.isPresent(), is(equalTo(true)));
    }

    @Test
    @Transactional
    public void whenGetAllEmployees_thenListReturned() {
        List<Employee> employees = employeeDAO.getAllEmployees();
        assertThat(employees.size(), is(equalTo(2)));
    }

    @Test
    @Transactional
    public void givenEmployee_whenAddEmployee_thenEmployeeExist() {
        Employee testEmployee = new Employee();
        testEmployee.setFirstName("TestName");
        testEmployee.setLastName("TestLastName");
        testEmployee.setDepartmentId(1);
        testEmployee.setJobTitle("TestJobTitle");
        testEmployee.setGender(Gender.FEMALE);

        employeeDAO.addEmployee(testEmployee);
        Optional<Employee> optional = employeeDAO.getEmployee(3);
        assertThat(optional.isPresent(), is(equalTo(true)));
    }

    @Test
    @Transactional
    @AfterTestMethod("givenEmployee_whenAddEmployee_thenEmployeeExist")
    public void givenEmployee_whenUpdate_thenEmployeeExistAndReturned() {
        Employee testEmployee = new Employee();
        testEmployee.setId(3);
        testEmployee.setFirstName("TestNameUpd");
        testEmployee.setLastName("TestLastNameUpd");
        testEmployee.setDepartmentId(2);
        testEmployee.setJobTitle("TestJobTitleUpd");
        testEmployee.setGender(Gender.MALE);

        employeeDAO.updateEmployee(testEmployee.getId(), testEmployee);
        Optional<Employee> optional = employeeDAO.getEmployee(testEmployee.getId());
        assertThat(optional.isPresent(), is(equalTo(true)));
        Employee employee = optional.get();
        assertThat(employee.getFirstName(), is(equalTo(testEmployee.getFirstName())));
        assertThat(employee.getLastName(), is(equalTo(testEmployee.getLastName())));
        assertThat(employee.getDepartmentId(), is(equalTo(testEmployee.getDepartmentId())));
        assertThat(employee.getJobTitle(), is(equalTo(testEmployee.getJobTitle())));
        assertThat(employee.getGender(), is(equalTo(testEmployee.getGender())));
    }

    @Test
    @Transactional
    @AfterTestMethod("givenEmployee_whenUpdate_thenEmployeeExistAndReturned")
    public void givenEmployeeId_whenDelete_thenEmployeeNotExist() {
        employeeDAO.deleteEmployee(3);
        Optional<Employee> optional = employeeDAO.getEmployee(3);
        assertThat(optional.isPresent(), is(equalTo(false)));
    }


}
