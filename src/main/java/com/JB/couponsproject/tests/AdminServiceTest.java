package com.JB.couponsproject.tests;

import com.JB.couponsproject.dto.CompanyDto;
import com.JB.couponsproject.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
@RequiredArgsConstructor
public class AdminServiceTest implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(AdminServiceTest.class);
    private final AdminService adminService;
    @Override
    public void run(String... args) throws Exception {
        logger.info("Starting Tests...");
        //Add company test
        logger.info("Adding new company");
        final CompanyDto company = adminService.createCompany(CompanyDto.builder().email("COMPANY@TEST.COM").name("Test Company LTD").password(123456).build());
        logger.info("Company added, "+company.toString());
        logger.info("Updating company");
        company.setName("Test Update name");
        adminService.updateCompany(company);
        logger.info("company updated: "+adminService.getCompanyById(company.getId()).toString());
    }
}
