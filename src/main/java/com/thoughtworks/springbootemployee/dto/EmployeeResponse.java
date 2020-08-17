package com.thoughtworks.springbootemployee.dto;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;

public class EmployeeResponse {

    private Integer id;
    private String name;
    private String gender;
    private String company_name;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public EmployeeResponse() {

    }
    public static EmployeeResponse from(Employee employee){
        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setId(employee.getEmployee_id());
        employeeResponse.setName(employee.getName());
        employeeResponse.setGender(employee.getGender());
        Company company = new Company();
        employeeResponse.setCompany_name(employee.getCompany().getName());
        return employeeResponse;
    }
}
