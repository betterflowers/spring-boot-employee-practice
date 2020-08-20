package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.entity.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

  @Mock
  EmployeeRepository employeeRepository;

  @Mock
  CompanyRepository companyRepository;

  @InjectMocks
  EmployeeService employeeService;

  @Test
  public void should_add_one_success_when_add_employee_given_one_employeeRequest(){
    //given
    Company company = new Company("oocl");
    EmployeeRequest employeeRequest = new EmployeeRequest();
    Employee employee = new Employee();
    when(companyRepository.findById(employeeRequest.getCompanyId())).thenReturn(Optional.of(company));
    when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

    //when
    employeeService.addEmployee(employeeRequest);

    //then
    Mockito.verify(employeeRepository).save(any(Employee.class));
  }

  @Test
  public void should_delete_one_success_when_delete_employee_given_one_employee_and_employeeId(){
    //given
    Employee employee = new Employee();
    Integer employeeId = employee.getId();

    //when
    employeeService.deleteEmployeeById(employeeId);

    //then
    Mockito.verify(employeeRepository).deleteById(employeeId);
  }

  @Test
  public void should_return_one_employeeResponse_when_get_by_employeeId_given_two_employee_and_employeeId(){
    //given
    Company company = new Company("oocl");
    Employee employee = new Employee("xiaoming");
    Employee employee1 = new Employee("test");
    employee.setCompany(company);
    Integer employeeId = employee.getId();
    when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

    //when
    EmployeeResponse employeeResponse = employeeService.getEmployeeById(employeeId);

    //then
    assertNotNull(employeeResponse);
    assertEquals("xiaoming",employeeResponse.getName());
  }

  @Test
  public void should_return_employeeResponse_list_when_get_by_gender_given_two_employees_and_gender(){
    //given
    Company company = new Company("oocl");
    List<Employee> employeeList = new ArrayList<>();
    Employee employeeFirst = new Employee("xiaoming");
    employeeFirst.setGender("Male");
    employeeFirst.setCompany(company);
    employeeList.add(employeeFirst);
    Employee employeeSecond = new Employee("xiaohong");
    employeeSecond.setGender("Male");
    employeeSecond.setCompany(company);
    employeeList.add(employeeSecond);
    String gender = "Male";
    when(employeeRepository.findByGender("Male")).thenReturn(employeeList);

    //when
    List<EmployeeResponse> employeeResponseList = employeeService.getEmployeeByGender(gender);

    //then
    assertEquals(2,employeeResponseList.size());
    Mockito.verify(employeeRepository).findByGender(gender);
  }
}
