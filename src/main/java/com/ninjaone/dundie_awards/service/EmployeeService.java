package com.ninjaone.dundie_awards.service;

import com.ninjaone.dundie_awards.exception.EmployeeNotFoundException;
import com.ninjaone.dundie_awards.model.Activity;
import com.ninjaone.dundie_awards.model.Employee;
import com.ninjaone.dundie_awards.repository.ActivityRepository;
import com.ninjaone.dundie_awards.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ActivityRepository activityRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee createEmployee(Employee employee) {
        activityRepository.save(Activity.employeeCreated(employee));
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    public Employee updateEmployee(Long id, Employee employeeDetails) {
        final var employee = getEmployeeById(id);
        final var activity = Activity.employeeUpdated(employee, employeeDetails);
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        activityRepository.save(activity);
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        final var employee = getEmployeeById(id);
        activityRepository.save(Activity.employeeDeleted(employee));
        employeeRepository.delete(employee);
    }
}
