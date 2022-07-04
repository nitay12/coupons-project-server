package com.JB.couponsproject.controllers;

import com.JB.couponsproject.dto.CompanyDto;
import com.JB.couponsproject.dto.CustomerDto;
import com.JB.couponsproject.entities.CompanyEntity;
import com.JB.couponsproject.entities.CustomerEntity;
import com.JB.couponsproject.exceptions.ApplicationException;
import com.JB.couponsproject.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("admin")
public class AdminController {

    private final AdminService adminService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/customers")
    public CustomerDto createCustomer(@RequestBody final CustomerDto customerDto) throws ApplicationException {
        return adminService.createCustomer(customerDto);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/companies")
    public CompanyDto createCompany(@RequestBody final CompanyDto companyDto) throws ApplicationException{
        return adminService.createCompany(companyDto);
    }

    @PutMapping("/customers")
    public CustomerDto updateCustomer(@RequestBody final CustomerDto customerDto) throws ApplicationException {
        return adminService.createCustomer(customerDto);
    }

    @PutMapping("/companies")
    public CompanyDto updateCompany(@RequestBody final CompanyDto companyDto) throws ApplicationException{
        return adminService.createCompany(companyDto);
    }

    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable final long id) throws ApplicationException{
        adminService.deleteCustomer(id);
    }

    @DeleteMapping("/companies/{id}")
    public void deleteCompany(@PathVariable final long id) throws ApplicationException{
        adminService.deleteCompany(id);
    }

    @GetMapping("/companies")
    public List<CompanyEntity> getAllCompanies(){
        return adminService.getAllCompanies();
    }

    @GetMapping("/customers")
    public List<CustomerEntity> getAllCustomers(){
        return adminService.getAllCustomers();
    }

    @GetMapping("/companies/{id}")
    public CompanyDto getCompanyById(@PathVariable final long id) throws ApplicationException {
        return adminService.getCompanyById(id);
    }

    @GetMapping("/1customers/{id}")
    public CustomerDto getCustomerById(@PathVariable final long id) throws ApplicationException {
        return adminService.getCustomerById(id);
    }


}
