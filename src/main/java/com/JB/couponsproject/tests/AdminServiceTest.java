package com.JB.couponsproject.tests;

import com.JB.couponsproject.constants.TestData;
import com.JB.couponsproject.dto.CompanyDto;
import com.JB.couponsproject.dto.CustomerDto;
import com.JB.couponsproject.entities.CompanyEntity;
import com.JB.couponsproject.entities.CustomerEntity;
import com.JB.couponsproject.repositories.CompanyRepository;
import com.JB.couponsproject.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.JB.couponsproject.constants.TestData.*;
@Order(2)
@Component
@RequiredArgsConstructor
public class AdminServiceTest implements CommandLineRunner
{
    private final Logger logger = LoggerFactory.getLogger(AdminServiceTest.class);
    private final CompanyRepository companyRepository;
    private final AdminService adminService;

    @Override
    public void run(String... args) throws Exception {
        logger.info("Starting Tests...");
        //Add company test
        logger.info("Adding new company");
        final CompanyDto company = adminService.createCompany(CompanyDto.builder()
                .email(TestData.ADMIN_COMPANY_EMAIL)
                .name(ADMIN_COMPANY_NAME)
                .password(TestData.ADMIN_COMPANY_PASSWORD)
                .build());
        logger.info("Company added, "+company.toString());
        logger.info("Get Company by: name, id, email");
        CompanyDto companyDto = adminService.getCompanyByName(ADMIN_COMPANY_NAME);
        logger.info("Get Company by: name\n" + companyDto.toString());
        long id = companyDto.getId();
        logger.info("Resetting company.");
        companyDto = adminService.getCompanyById(id);
        logger.info("Get Company by: id\n" + companyDto.toString());
        logger.info("Resetting company.");
        companyDto = adminService.getCompanyByEmail(ADMIN_COMPANY_EMAIL);
        logger.info("Get Company by: email\n" + companyDto.toString());
        //Update company test
        logger.info("Updating company");
        companyDto.setName(ADMIN_COMPANY_NAME_UPDATE);
        companyDto.setEmail(ADMIN_COMPANY_EMAIL_UPDATE);
        adminService.createCompany(companyDto);
        logger.info("company updated: "+adminService.getCompanyById(company.getId()).toString());

        //Add customer test
        logger.info("Adding new customer");
        final CustomerDto customer = adminService.createCustomer(CustomerDto.builder()
                .email(ADMIN_CUSTOMER_EMAIL)
                .firstName(ADMIN_CUSTOMER_F_NAME)
                .lastName(ADMIN_CUSTOMER_L_NAME)
                .password(ADMIN_CUSTOMER_PASSWORD)
                .build());
        logger.info("Customer added, "+customer.toString());
        //Get customer tests
        CustomerDto customerDto = adminService.getCustomerByEmail(ADMIN_CUSTOMER_EMAIL);
        logger.info("Get Customer by: email\n" + customerDto.toString());
        id = customerDto.getId();
        logger.info("Resetting customer.");
        customerDto = adminService.getCustomerById(id);
        logger.info("Get Customer by: Id\n" + customerDto.toString());
        //Update customer test
        logger.info("Updating this customer");
        customerDto.setFirstName(ADMIN_CUSTOMER_F_NAME_UPDATE);
        customerDto.setLastName(ADMIN_CUSTOMER_L_NAME_UPDATE);
        customerDto.setEmail(ADMIN_CUSTOMER_EMAIL_UPDATE);
        adminService.createCustomer(customerDto);
        logger.info("Customer updated, "+customerDto);

        //Get All
        //?
        List<CompanyEntity> companies = adminService.getAllCompanies();
        List<CustomerEntity> customers = adminService.getAllCustomers();

        //delete company test
        logger.info("About to delete company: "+companyDto.getName());
        adminService.deleteCompany(companyDto.getId());
        logger.info("Deleted company: "+companyDto.getName());
        //Delete customer test
        logger.info("About to delete customer: "+customerDto.getEmail());
        adminService.deleteCustomer(customerDto.getId());
        logger.info("Deleted customer: "+customerDto.getEmail());

    }
}
