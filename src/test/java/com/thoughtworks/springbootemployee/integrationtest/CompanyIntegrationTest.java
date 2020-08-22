package com.thoughtworks.springbootemployee.integrationtest;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
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
public class CompanyIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @AfterEach
    void clean() {
        employeeRepository.deleteAll();
        companyRepository.deleteAll();
    }

    @Test
    public void should_return_one_company_when_add_company_given_one_company() throws Exception {

        String companyContent = "{\"name\":\"oocl\"}";
        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyContent))
                .andExpect(status().isCreated());

        List<Company> companyList = companyRepository.findAll();

        assertEquals(1, companyList.size());

    }

    @Test
    public void should_return_one_company_when_get_company_by_companyId_given_one_company_and_companyId() throws Exception {
        Company company = new Company("oocl");
        company = companyRepository.save(company);
        mockMvc.perform(get("/companies/" + company.getCompanyId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("oocl"));

    }

    @Test
    public void should_return_zero_company_when_delete_company_given_one_company() throws Exception {
        Company company = new Company("cosco");
        company = companyRepository.save(company);
        mockMvc.perform(delete("/companies/" + company.getCompanyId()))
                .andExpect(status().isOk());

        List<Company> companyList = companyRepository.findAll();
        assertEquals(0, companyList.size());
    }

    @Test
    public void should_return_modify_company_when_update_company_given_old_company_and_modify_company_companyId() throws Exception {
        Company company = new Company("cosco");
        company = companyRepository.save(company);

        String companyContent = "{\"name\":\"oocl\"}";

        mockMvc.perform(put("/companies/" + company.getCompanyId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyContent))
                .andExpect(status().isOk());

        List<Company> companyList = companyRepository.findAll();
        assertEquals("oocl", companyList.get(0).getName());

    }

    //bug
    @Test
    public void should_return_one_employee_when_get_company_by_companyId_given_one_company_and_companyId() throws Exception {

        Company company = new Company("oocl");
        company = companyRepository.save(company);

        Employee employee = new Employee();
        employee.setCompany(company);
        employee.setName("xiaoming");
        employee.setAge(20);
        employee.setGender("Male");
        employeeRepository.save(employee);

        mockMvc.perform(get("/companies/" + company.getCompanyId() + "/employees")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].companyName").value("oocl"));
    }
}
