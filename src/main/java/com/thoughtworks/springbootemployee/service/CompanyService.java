package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.exception.CompanyNotFonudException;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public void deleteCompanyById(Integer companyId) {
        companyRepository.deleteById(companyId);
    }

    public void addCompany(Company company) {
        companyRepository.save(company);
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company getCompanyId(Integer companyId) {
        return companyRepository.findById(companyId).orElseThrow(CompanyNotFonudException::new);
    }

    public void updateCompanyById(Integer companyId, Company company) {
        company.setCompanyId(companyId);
        companyRepository.save(company);
    }
}
