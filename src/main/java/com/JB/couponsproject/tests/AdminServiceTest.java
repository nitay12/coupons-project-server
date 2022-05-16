package com.JB.couponsproject.tests;

import com.JB.couponsproject.dto.CompanyDto;
import com.JB.couponsproject.dto.CustomerDto;
import com.JB.couponsproject.entities.CompanyEntity;
import com.JB.couponsproject.entities.CustomerEntity;
import com.JB.couponsproject.login.LoginManager;
import com.JB.couponsproject.repositories.CompanyRepository;
import com.JB.couponsproject.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(2)
@RequiredArgsConstructor
public class AdminServiceTest implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(AdminServiceTest.class);
    private final CompanyRepository companyRepository;
    private final AdminService adminService;
    @Override
    public void run(String... args) throws Exception {
        logger.info("Starting Tests...");
        //Add company test
        logger.info("Adding new company");
        final CompanyEntity company = adminService.createCompany(CompanyEntity.builder().email("COMPANY@TEST.COM").name("Test Company LTD").password(123456).build());
        logger.info("Company added, "+company.toString());
        logger.info("Get Company by: name, id, email");
        CompanyDto companyDto = adminService.getCompanyByName("Test Company LTD");
        logger.info("Get Company by: name\n" + companyDto.toString());
        long id = companyDto.getId();
        logger.info("Resetting company.");
        companyDto  = null;
        companyDto = adminService.getCompanyById(id);
        logger.info("Get Company by: id\n" + companyDto.toString());
        logger.info("Resetting company.");
        companyDto  = null;
        companyDto = adminService.getCompanyByEmail("COMPANY@TEST.COM");
        logger.info("Get Company by: email\n" + companyDto.toString());
        //Update company test
        logger.info("Updating company");
        company.setName("Test Update name");
        adminService.updateCompany(companyDto);
        logger.info("company updated: "+adminService.getCompanyById(company.getId()).toString());

        //Add customer test
        logger.info("Adding new customer");
        final CustomerEntity customer = adminService.createCustomer(CustomerEntity.builder().email("CUSTOMER@TEST.COM").firstName("FTEST").lastName("LTEST").password(123456).build());
        logger.info("Customer added, "+customer.toString());
        //Get customer tests
        CustomerDto customerDto = adminService.getCustomerByEmail("CUSTOMER@TEST.COM");
        logger.info("Get Customer by: email\n" + customerDto.toString());
        id = customerDto.getId();
        logger.info("Resetting customer.");
        customerDto  = null;
        customerDto = adminService.getCustomerById(id);
        logger.info("Get Customer by: Id\n" + customerDto.toString());
        //Update customer test
        logger.info("Updating this customer");
        customerDto.setFirstName("First");
        customerDto.setLastName("Last");
        adminService.updateCustomer(customerDto);
        logger.info("Customer updated, "+customerDto.toString());

        //Get All
        List<CompanyEntity> companies = adminService.getAllCompanies();
        List<CustomerEntity> customers = adminService.getAllCustomers();

        //delete company test
        logger.info("About to delete company: "+companyDto.getName());
        adminService.deleteCompany(companyDto);
        logger.info("Deleted company: "+companyDto.getName());
        //Delete customer test
        logger.info("About to delete customer: "+customerDto.getEmail());
        adminService.deleteCustomer(customerDto);
        logger.info("Deleted customer: "+customerDto.getEmail());

    }
}
