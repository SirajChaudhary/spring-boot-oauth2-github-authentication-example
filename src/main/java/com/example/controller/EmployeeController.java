package com.example.demo.controller;

import com.example.dto.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * REST Controller to manage Employees (CRUD operations)
 * Uses in-memory storage for demonstration purposes.
 */
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    // In-memory store for employees
    private final Map<Long, Employee> employeeMap = new HashMap<>();
    private long counter = 1; // auto-increment ID

    /**
     * Get all employees
     */
    @GetMapping
    public Collection<Employee> getAllEmployees() {
        return employeeMap.values();
    }

    /**
     * Get a single employee by ID
     */
    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable Long id) {
        return employeeMap.getOrDefault(id, null);
    }

    /**
     * Create a new employee
     */
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        long id = counter++;
        employee.setId(id); // assign ID
        employeeMap.put(id, employee);
        return employee;
    }

    /**
     * Update an existing employee
     */
    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        if (!employeeMap.containsKey(id)) {
            return null;
        }
        employee.setId(id); // keep the same ID
        employeeMap.put(id, employee);
        return employee;
    }

    /**
     * Delete an employee
     */
    @DeleteMapping("/{id}")
    public Map<String, String> deleteEmployee(@PathVariable Long id) {
        if (employeeMap.remove(id) != null) {
            return Map.of("message", "Deleted employee " + id);
        } else {
            return Map.of("error", "Employee not found");
        }
    }
}
