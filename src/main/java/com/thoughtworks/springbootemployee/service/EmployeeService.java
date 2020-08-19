package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.exception.CompanyNotFonudException;
import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;

    public EmployeeService(EmployeeRepository employeeRepository, CompanyRepository companyRepository) {
        this.employeeRepository = employeeRepository;
        this.companyRepository = companyRepository;
    }

    public void addEmployee(EmployeeRequest employeeRequest) {
        Employee employee = EmployeeRequest.requestMapToEntity(employeeRequest);
        Company company = companyRepository.findById(employeeRequest.getCompanyId()).orElseThrow(EmployeeNotFoundException::new);
        employee.setCompany(company);
        employeeRepository.save(employee);
    }

    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(EmployeeResponse::EntityMapToResponse)
                .collect(Collectors.toList());
    }

    public void deleteEmployeeById(Integer employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    public EmployeeResponse getEmployeeById(Integer employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(EmployeeNotFoundException::new);
        EmployeeResponse employeeResponse = EmployeeResponse.EntityMapToResponse(employee);
        return employeeResponse;
    }

    public List<EmployeeResponse> getEmployeeByGender(String gender) {
        return employeeRepository.findByGender(gender).stream()
                .map(EmployeeResponse::EntityMapToResponse)
                .collect(Collectors.toList());
    }

    public void updateEmployeeById(Integer employeeId, EmployeeRequest employeeRequest) {
        Employee employee = EmployeeRequest.requestMapToEntity(employeeRequest);
        Company company = companyRepository.findById(employeeRequest.getCompanyId()).orElseThrow(CompanyNotFonudException::new);
        employee.setId(employeeId);
        employee.setCompany(company);
        employeeRepository.save(employee);
    }

    public List<EmployeeResponse> getEmployeeByPage(Pageable pageable) {
        return employeeRepository.findAll(pageable).stream()
                .map(EmployeeResponse::EntityMapToResponse)
                .collect(Collectors.toList());
    }
}
