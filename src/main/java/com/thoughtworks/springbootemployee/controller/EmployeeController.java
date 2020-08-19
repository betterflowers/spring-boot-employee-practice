package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(params = {"gender"})
    public List<EmployeeResponse> getEmployeeByGender(@RequestParam String gender) {
        return employeeService.getEmployeeByGender(gender);
    }

    @PostMapping
    public void addEmployee(@RequestBody EmployeeRequest employeeRequest) {
        employeeService.addEmployee(employeeRequest);
    }

    @GetMapping
    public List<EmployeeResponse> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @DeleteMapping("/{employeeId}")
    public void deleteEmployeeById(@PathVariable Integer employeeId) {
        employeeService.deleteEmployeeById(employeeId);
    }

    @GetMapping("/{employeeId}")
    public EmployeeResponse getEmployeeById(@PathVariable Integer employeeId) {
        return employeeService.getEmployeeById(employeeId);
    }

    @PutMapping("/{employeeId}")
    public void updateEmployeeById(@PathVariable Integer employeeId, @RequestBody EmployeeRequest employeeRequest) {
        employeeService.updateEmployeeById(employeeId, employeeRequest);
    }
}
