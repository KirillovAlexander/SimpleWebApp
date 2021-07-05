package com.alexkirillov.simplewebapp.mq;

import com.alexkirillov.simplewebapp.dao.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class EmployeeConsumer {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeConsumer.class);
    private final EmployeeRepository employeeRepository;

    public EmployeeConsumer(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @JmsListener(destination = "EmployeesServiceDeleteEmployee")
    public void deleteEmployee(long id) {
        logger.info("deleteEmployee(id) with id = {}", id);
        employeeRepository.deleteById(id);
    }

}
