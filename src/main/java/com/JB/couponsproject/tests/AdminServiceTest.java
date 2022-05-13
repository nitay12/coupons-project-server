package com.JB.couponsproject.tests;

import com.JB.couponsproject.dto.CompanyDto;
import com.JB.couponsproject.entities.CompanyEntity;
import com.JB.couponsproject.login.LoginManager;
import com.JB.couponsproject.repositories.CompanyRepository;
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
    private final CompanyRepository companyRepository;
    private final AdminService adminService;
    @Override
    public void run(String... args) throws Exception {
        logger.info("Starting Tests...");
        //Add company test
        logger.info("Adding new company");
        final CompanyEntity company = adminService.createCompany(CompanyEntity.builder().email("COMPANY@TEST.COM").name("Test Company LTD").password(123456).build());
        CompanyDto companyDto = companyRepository.findByName("Test Company LTD").get(0);
        logger.info("Company added, "+company.toString());
        logger.info("Updating company");
        company.setName("Test Update name");
        adminService.updateCompany(companyDto);
        logger.info("company updated: "+adminService.getCompanyById(company.getId()).toString());
    }
}
