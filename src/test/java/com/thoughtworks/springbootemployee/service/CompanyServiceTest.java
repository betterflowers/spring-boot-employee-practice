package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    public void should_return_one_size_when_add_company_given_company() {
        //given
        Company company = new Company("oocl");
        when(companyRepository.save(any(Company.class))).thenReturn(company);

        //when
        Company companyAdd = companyService.addCompany(company);

        //then
        assertNotNull(companyAdd);
    }


}
