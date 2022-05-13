package com.JB.couponsproject.util;

import com.JB.couponsproject.dto.CompanyDto;
import com.JB.couponsproject.dto.CouponDto;
import com.JB.couponsproject.dto.CustomerDto;
import com.JB.couponsproject.entities.CompanyEntity;
import com.JB.couponsproject.entities.CustomerEntity;
import com.JB.couponsproject.enums.Category;
import com.JB.couponsproject.exceptions.ApplicationException;
import com.JB.couponsproject.repositories.CompanyRepository;
import com.JB.couponsproject.repositories.CouponRepository;
import com.JB.couponsproject.repositories.CustomerRepository;
import com.JB.couponsproject.services.AdminService;
import com.JB.couponsproject.services.CompanyService;
import com.JB.couponsproject.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Order(1)
public class MockDataInserter implements CommandLineRunner {
    private final AdminService adminService;
    private final CustomerService customerService;
    private final CompanyService companyService;
    private final Logger logger = LoggerFactory.getLogger(MockDataInserter.class);
    public void insert() throws ApplicationException {
        logger.info("Inserting mock data to the DB");
        for (int i = 1; i <= 10; i++) {
            final CompanyEntity newCompany = adminService.createCompany
                    (new CompanyEntity(
                    "company" + i,
                    "company" + i + "@email.com",
                    "123456"));
            logger.debug("New company added:" + newCompany.toString());
            companyService.login(
                    newCompany.getEmail(),"123456"
            );
            final Long newCouponId = companyService.addCoupon(
                    new CouponDto(
                            Category.ELECTRICITY,
                            "title"+i,
                            "desc",
                            LocalDate.of(2022, 2, 2),
                            LocalDate.of(2022, 5, 10),
                            300,
                            100,
                            "https://company/image.jpg"
                    ),1L
            );
            final CustomerEntity newCustomer = adminService.createCustomer(new CustomerEntity(
                    "customer" + i,
                    "last name",
                    "customer" + i + "@email.com",
                    "123456"
            ));
            logger.debug("New customer was added to the DB: " + newCustomer.toString());
            customerService.purchaseCoupon(newCouponId,newCustomer.getId());
            logger.debug(newCustomer.getFirstName()+" purchased coupon");

        }
    }

    @Override
    public void run(String... args) throws Exception {
        insert();
    }
}
