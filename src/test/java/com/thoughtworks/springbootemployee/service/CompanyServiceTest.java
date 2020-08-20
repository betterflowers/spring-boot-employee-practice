package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {

  @Mock
  private CompanyRepository companyRepository;
  @InjectMocks
  private CompanyService companyService;

  @Test
  public void should_return_zero_when_delete_one_company_given_one_company() {
    //given
    Company company = new Company("oocl");
    Integer companyId = 1;

    //when
    companyService.deleteCompanyById(companyId);

    //then
    Mockito.verify(companyRepository).deleteById(companyId);
  }

  @Test
  public void should_return_one_size_when_add_company_given_company() {
    //given
    Company company = new Company("oocl");
    when(companyRepository.save(any(Company.class))).thenReturn(company);

    //when
    companyService.addCompany(company);

    //then
    Mockito.verify(companyRepository).save(any(Company.class));
  }

  @Test
  public void should_return_companyOne_when_search_company_By_Id_given_companyId_one() {
    //given
    Integer companyId = 1;
    Company company = new Company("cosco");
    when(companyRepository.findById(companyId)).thenReturn(Optional.of(company));

    //when
    Company companyTest = companyService.getCompanyById(companyId);

    //then
    assertNotNull(companyTest);
  }

  @Test
  public void should_return_new_company_when_update_given_new_company() {
    //given
    Company company = new Company("oocl");
    Company newCompany = new Company("cosco");
    Integer companyId = 1;
    when(companyRepository.save(any(Company.class))).thenReturn(company);

    //when
    companyService.updateCompanyById(companyId, newCompany);

    //then
    Mockito.verify(companyRepository).save(any(Company.class));
  }

  @Test
  public void should_return_two_employees_when_get_employee_by_companyId_given_2_employees_and_1_company() {
    //given
    Company company = new Company("oocl");

    company.setEmployee(new ArrayList<>());

    Employee employeeFirst = new Employee();
    Employee employeeSecond = new Employee();
    employeeFirst.setCompany(company);
    employeeSecond.setCompany(company);
    company.getEmployee().add(employeeFirst);
    company.getEmployee().add(employeeSecond);
    Integer companyId = 1;
    when(companyRepository.findById(companyId)).thenReturn(Optional.of(company));

    //when
    List<EmployeeResponse> employeeResponse = companyService.getEmployeeByCompanyId(companyId);

    //then
    assertEquals(2, employeeResponse.size());

  }

  @Test
  public void should_return_company_list_when_get_all_companies_given_page_and_size() {
    //given
    Integer page = 1;
    Integer size = 2;
    Pageable pageable = PageRequest.of(page, size);
    when(companyRepository.findAll(any(Pageable.class))).thenReturn(Page.empty());

    //when
    companyService.getCompanyByPage(pageable);

    //then
    Mockito.verify(companyRepository).findAll(any(Pageable.class));
  }
}
