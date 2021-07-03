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
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/*
    Tests use data from test/resources/db/changelog/v-1.1/10-insert-employee-data.sql
 */

@SpringBootTest
@ContextConfiguration(initializers = {RepositoryTest.Initializer.class})
@Testcontainers
public class RepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;

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
                    "spring.liquibase.change-log=classpath:db/changelog/db.changelog-test.xml",
                    "spring.jpa.hibernate.ddl-auto=validate"
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Test
    public void givenID_whenGetEmployee_thenEmployeeReturned() {
        Optional<Employee> optional = employeeRepository.findById(1L);
        assertThat(optional.isPresent(), is(equalTo(true)));
    }

    @Test
    public void whenGetAllEmployees_thenListReturned() {
        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees.size(), is(equalTo(2)));
    }

    @Test
    public void givenEmployee_whenAddEmployee_thenEmployeeExist() {
        Employee testEmployee = new Employee();
        testEmployee.setFirstName("TestName");
        testEmployee.setLastName("TestLastName");
        testEmployee.setDepartmentId(1);
        testEmployee.setJobTitle("TestJobTitle");
        testEmployee.setGender(Gender.FEMALE);

        employeeRepository.save(testEmployee);
        Optional<Employee> optional = employeeRepository.findById(3L);
        assertThat(optional.isPresent(), is(equalTo(true)));
    }

    @Test
    public void givenEmployee_whenUpdate_thenEmployeeExistAndReturned() {
        Employee testEmployee = new Employee();
        testEmployee.setId(2L);
        testEmployee.setFirstName("TestNameUpd");
        testEmployee.setLastName("TestLastNameUpd");
        testEmployee.setDepartmentId(2);
        testEmployee.setJobTitle("TestJobTitleUpd");
        testEmployee.setGender(Gender.MALE);

        employeeRepository.save(testEmployee);
        Optional<Employee> optional = employeeRepository.findById(testEmployee.getId());
        assertThat(optional.isPresent(), is(equalTo(true)));
        Employee employee = optional.get();
        assertThat(employee.getFirstName(), is(equalTo(testEmployee.getFirstName())));
        assertThat(employee.getLastName(), is(equalTo(testEmployee.getLastName())));
        assertThat(employee.getDepartmentId(), is(equalTo(testEmployee.getDepartmentId())));
        assertThat(employee.getJobTitle(), is(equalTo(testEmployee.getJobTitle())));
        assertThat(employee.getGender(), is(equalTo(testEmployee.getGender())));
    }

    @Test
    public void givenEmployeeId_whenDelete_thenEmployeeNotExist() {
        employeeRepository.deleteById(1L);
        Optional<Employee> optional = employeeRepository.findById(1L);
        assertThat(optional.isPresent(), is(equalTo(false)));
    }


}
