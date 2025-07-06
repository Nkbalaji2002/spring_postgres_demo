package com.example.springdemotwo.controller;

import com.example.springdemotwo.exception.ResourceNotFoundException;
import com.example.springdemotwo.model.Employee;
import com.example.springdemotwo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    /* Get All Employees */
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    /* get employee by id */
    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id : " + id));

        return ResponseEntity.ok(employee);
    }

    /* Create employees */
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    /* Update Employee */
    @PutMapping("{id}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employeeDetails, @PathVariable Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id : " + id));

        employee.setFirstName(employee.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmail(employeeDetails.getEmail());

        Employee updatedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    /* delete employee */
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id : " + id));

        employeeRepository.delete(employee);
        return new ResponseEntity<>("Employee Deleted Successfully", HttpStatus.OK);
    }

}
