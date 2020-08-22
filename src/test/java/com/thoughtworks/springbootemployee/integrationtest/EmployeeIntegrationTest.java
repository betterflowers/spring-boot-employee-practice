package com.thoughtworks.springbootemployee.integrationtest;

import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    CompanyRepository companyRepository;

    @AfterEach
    void clean() {
        employeeRepository.deleteAll();
        companyRepository.deleteAll();
    }

    @Test
    public void should_return_success_when_add_employee_given_one_company_and_one_employeeRequest() throws Exception {
        Company company = new Company("oocl");
        company = companyRepository.save(company);
        String employeeContent = "  {\n" +
                "      \"name\": \"red\",\n" +
                "      \"gender\": \"Male\",\n" +
                "      \"age\": 20,\n" +
                "      \"companyId\": " + company.getCompanyId() + " \n" +
                "}";
        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeContent))
                .andExpect(status().isCreated());

        List<Employee> employeeResponseList = employeeRepository.findAll();
        assertEquals(1, employeeResponseList.size());
    }

    @Test
    public void should_delete_success_when_delete_employee_by_employeeId_given_company_and_employee_and_employeeId() throws Exception {
        Company company = new Company("oocl");
        company = companyRepository.save(company);
        Employee employee = new Employee();
        employee.setCompany(company);
        employee.setGender("Male");
        employee.setAge(20);
        employee.setName("xiaoming");
        employeeRepository.save(employee);

        mockMvc.perform(delete("/employees/" + employee.getId()))
                .andExpect(status().isOk());

        List<Employee> employeeList = employeeRepository.findAll();
        assertEquals(0, employeeList.size());

    }

    @Test
    public void should_return_employee_by_employeeId_when_find_by_employeeId_given_company_and_employee_and_employeeId() throws Exception {
        Company company = new Company("oocl");
        company = companyRepository.save(company);
        Employee employee = new Employee();
        employee.setName("xiaoming");
        employee.setAge(20);
        employee.setGender("Male");
        employee.setCompany(company);
        Integer employeeId = employeeRepository.save(employee).getId();

        mockMvc.perform(get("/employees/" + employeeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("xiaoming"))
                .andExpect(jsonPath("companyName").value("oocl"));
    }

    @Test
    public void should_return_modify_employee_when_update_employee_by_employeeId_when_given_company_and_employee_and_employeeId() throws Exception {
        Company company = new Company("oocl");
        company = companyRepository.save(company);
        Employee employee = new Employee();
        employee.setName("xiaoming");
        employee.setAge(20);
        employee.setGender("Male");
        employee.setCompany(company);
        Integer employeeId = employeeRepository.save(employee).getId();

        String employeeUpdate = "  {\n" +
                "      \"name\": \"red\",\n" +
                "      \"gender\": \"Male\",\n" +
                "      \"age\": 20,\n" +
                "      \"companyId\":" + company.getCompanyId() + " \n" +
                "}";

        mockMvc.perform(put("/employees/" + employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeUpdate))
                .andExpect(status().isOk());

        Employee employeeTest = employeeRepository.findById(employeeId).orElseThrow(EmployeeNotFoundException::new);
        assertEquals("red", employeeTest.getName());

    }

    @Test
    void should_return_1_employee_when_get_employee_by_page_given_page_1_size_1() throws Exception {
        Company company = new Company();
        company.setName("cargosmart");
        company = companyRepository.save(company);

        Employee employee = new Employee();
        employee.setName("xiaoming");
        employee.setAge(1);
        employee.setGender("male");
        employee.setCompany(company);
        employeeRepository.save(employee);

        Employee employee1 = new Employee();
        employee1.setName("xiaohong");
        employee1.setAge(2);
        employee1.setGender("female");
        employee1.setCompany(company);
        employeeRepository.save(employee1);

        mockMvc
                .perform(get("/employees?page=1&pageSize=2"))
                .andExpect(status().isOk());
    }


}
