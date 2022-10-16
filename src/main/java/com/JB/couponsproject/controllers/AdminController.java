package com.JB.couponsproject.controllers;

import com.JB.couponsproject.dto.CompanyDto;
import com.JB.couponsproject.dto.CustomerDto;
import com.JB.couponsproject.entities.CompanyEntity;
import com.JB.couponsproject.entities.CustomerEntity;
import com.JB.couponsproject.enums.UserType;
import com.JB.couponsproject.exceptions.ApplicationException;
import com.JB.couponsproject.exceptions.ForbiddenException;
import com.JB.couponsproject.exceptions.UpdateException;
import com.JB.couponsproject.security.JwtUtil;
import com.JB.couponsproject.security.JwtWrapper;
import com.JB.couponsproject.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("admin")
@CrossOrigin()
public class AdminController {
    private final AdminService adminService;

    boolean isAdmin(JwtWrapper jwtHeader) {
        final String token = jwtHeader.getToken();
        return JwtUtil.extractUserType(token).equals(UserType.ADMIN);
    }

    private void isAdminOrForbidden(JwtWrapper jwtHeader) throws ForbiddenException {
        if (!isAdmin(jwtHeader)) {
            throw new ForbiddenException(JwtUtil.extractUserType(jwtHeader.getToken()));
        }
    }

    @PostMapping("companies")
    public CompanyDto addCompany(@RequestBody CompanyDto company, @RequestHeader("Authorization") JwtWrapper jwtHeader) throws ApplicationException {
        isAdminOrForbidden(jwtHeader);
        return adminService.createCompany(company);
    }

    @PutMapping("companies")
    public CompanyDto updateCompany(@RequestBody CompanyDto company, @RequestHeader("Authorization") JwtWrapper jwtHeader) throws ApplicationException {
        //TODO: return the update company method to adminService
        isAdminOrForbidden(jwtHeader);
        return adminService.updateCompany(company);
    }

    @DeleteMapping("companies/{id}")
    public void deleteCompany(@PathVariable("id") final Long id, @RequestHeader("Authorization") JwtWrapper jwtHeader) throws ApplicationException {
        isAdminOrForbidden(jwtHeader);
        adminService.deleteCompany(id);
    }

    @GetMapping("companies")
    public List<CompanyEntity> getAllCompanies(@RequestHeader("Authorization") JwtWrapper jwtHeader) throws ApplicationException {
        isAdminOrForbidden(jwtHeader);

        return adminService.getAllCompanies();
    }

    @GetMapping("companies/{id}")
    public CompanyDto getOneCompany(@PathVariable("id") final Long id, @RequestHeader("Authorization") JwtWrapper jwtHeader) throws ApplicationException {
        isAdminOrForbidden(jwtHeader);
        return adminService.getCompanyById(id);
    }

    @PostMapping("customers")
    public CustomerDto addCustomer(@RequestBody CustomerDto customer) throws ApplicationException {
        return adminService.createCustomer(customer);
    }

    @PutMapping("customers")
    public CustomerDto updateCustomer(@RequestBody CustomerDto customer) throws UpdateException {
        return adminService.updateCustomer(customer);
    }

    @DeleteMapping("customers/{id}")
    public void deleteCustomer(@PathVariable("id") final Long id) throws ApplicationException {
        adminService.deleteCustomer(id);
    }
    
    @GetMapping("customers")
    public List<CustomerEntity> getAllCustomers() {
        return adminService.getAllCustomers();
    }

    @GetMapping("customers/{id}")
    public CustomerDto getOneCustomer(@PathVariable("id") final Long id) throws ApplicationException {
        return adminService.getCustomerById(id);
    }
}
