package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.entity.Company;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PutMapping("/{companyId}")
    public void updateCompanyById(@PathVariable Integer companyId, @RequestBody Company company) {
        companyService.updateCompanyById(companyId, company);
    }

    @PostMapping
    public void addCompany(@RequestBody Company company) {
        companyService.addCompany(company);
    }

    @DeleteMapping("/{companyId}")
    public void deleteCompany(@PathVariable Integer companyId) {
        companyService.deleteCompanyById(companyId);
    }

    @GetMapping("/{companyId}")
    public Company getCompanyById(@PathVariable Integer companyId) {
        return companyService.getCompanyById(companyId);
    }

    @GetMapping("/{companyId}/employees")
    public List<EmployeeResponse> getEmployeesByCompanyId(@PathVariable Integer companyId) {
        return companyService.getEmployeeByCompanyId(companyId);
    }

    @GetMapping
    public Page<Company> getCompanyByPage(Pageable pageable, @RequestParam(defaultValue = "true") boolean unpaged) {
        if (unpaged) {
            return companyService.getCompanyByPage(Pageable.unpaged());
        }
        return companyService.getCompanyByPage(pageable);
    }
}
