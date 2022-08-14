package com.JB.couponsproject.controllers;

import com.JB.couponsproject.dto.CompanyDto;
import com.JB.couponsproject.dto.CustomerDto;
import com.JB.couponsproject.entities.CompanyEntity;
import com.JB.couponsproject.entities.CustomerEntity;
import com.JB.couponsproject.exceptions.ApplicationException;
import com.JB.couponsproject.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("admin")
public class AdminController {
    private final AdminService adminService;

    @PostMapping("companies")
    public CompanyDto addCompany(@RequestBody CompanyDto company) throws ApplicationException {
        return adminService.createCompany(company);
    }
    @PutMapping("companies")
    public CompanyDto updateCompany(@RequestBody CompanyDto company){
        //TODO: return the update company method to adminService
        return CompanyDto.builder().build();
//        return adminService.updateCompany;
    }
    @DeleteMapping("companies/{id}")
    public void deleteCompany(@PathVariable("id") final Long id) throws ApplicationException {
        adminService.deleteCompany(id);
    }
    //TODO: change the getAllCompanies in admin service to List<CompanyDto> for consistency?
    @GetMapping("companies")
    public List<CompanyEntity> getAllCompanies(){
        return adminService.getAllCompanies();
    }
    @GetMapping("companies/{id}")
    public CompanyDto getOneCompany(@PathVariable("id") final Long id) throws ApplicationException {
        return adminService.getCompanyById(id);
    }
    @PostMapping("customers")
    public CustomerDto addCustomer(@RequestBody CustomerDto customer) throws ApplicationException {
        return adminService.createCustomer(customer);
    }
    @PutMapping("customers")
    public CustomerDto updateCustomer(@RequestBody CustomerDto customer){
        //TODO: return the update customer method to adminService
        return CustomerDto.builder().build();
//        return adminService.updateCustomer;
    }
    @DeleteMapping("customers/{id}")
    public void deleteCustomer(@PathVariable("id") final Long id) throws ApplicationException {
        adminService.deleteCustomer(id);
    }
    //TODO: change the getAllCompanies in admin service to List<CompanyDto> for consistency?
    @GetMapping("customers")
    public List<CustomerEntity> getAllCustomers(){
        return adminService.getAllCustomers();
    }
    @GetMapping("customers/{id}")
    public CustomerDto getOneCustomer(@PathVariable("id") final Long id) throws ApplicationException {
        return adminService.getCustomerById(id);
    }
}
