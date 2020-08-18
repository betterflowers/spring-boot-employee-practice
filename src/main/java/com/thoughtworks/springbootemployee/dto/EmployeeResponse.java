package com.thoughtworks.springbootemployee.dto;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;

public class EmployeeResponse {

    private Integer id;
    private String name;
    private String gender;
    private String companyName;

    public EmployeeResponse() {

    }
    
    public static EmployeeResponse EntityMapToResponse(Employee employee) {
        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setId(employee.getId());
        employeeResponse.setName(employee.getName());
        employeeResponse.setGender(employee.getGender());
        employeeResponse.setCompanyName(employee.getCompany().getName());
        return employeeResponse;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
