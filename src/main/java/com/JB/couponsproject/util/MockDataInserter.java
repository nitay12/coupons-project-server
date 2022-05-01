package com.JB.couponsproject.util;

import com.JB.couponsproject.entities.CompanyEntity;
import com.JB.couponsproject.entities.CouponEntity;
import com.JB.couponsproject.entities.CustomerEntity;
import com.JB.couponsproject.enums.Category;
import com.JB.couponsproject.exceptions.ApplicationException;
import com.JB.couponsproject.repositories.CompanyRepository;
import com.JB.couponsproject.repositories.CouponRepository;
import com.JB.couponsproject.repositories.CustomerRepository;
import com.JB.couponsproject.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class MockDataInserter implements CommandLineRunner {
    final CompanyRepository companyRepository;
    final CouponRepository couponRepository;
    final CustomerRepository customerRepository;
    final CustomerService customerService;
//    final CouponVsCustomersRepository couponVsCustomersRepository;
    Logger logger = LoggerFactory.getLogger(MockDataInserter.class);
    public void insert() throws ApplicationException {
        logger.info("Inserting mock data to the DB");
        for (int i = 1; i <= 10; i++) {
            final CompanyEntity newCompany = companyRepository.save(new CompanyEntity(
                    "company" + i,
                    "company" + i + "@email.com",
                    "123456"
            ));
            logger.info("New company added:" + newCompany.toString());
            final CouponEntity newCoupon = couponRepository.save(
                    new CouponEntity(
                            newCompany.getId(),
                            Category.ELECTRICITY,
                            "title",
                            "desc",
                            LocalDate.of(2022, 2, 2),
                            LocalDate.of(2022, 5, 10),
                            300,
                            100,
                            "https://company/image.jpg"
                    )
            );
            final CustomerEntity newCustomer = customerRepository.save(new CustomerEntity(
                    "customer" + i,
                    "last name",
                    "customer" + i + "@email.com",
                    "123456"
            ));
            logger.info("New customer was added to the DB: " + newCustomer.toString());
            customerService.purchaseCoupon(newCoupon.getId(),newCustomer.getId());
            customerRepository.save(newCustomer);
            logger.info(newCustomer.getFirstName()+" purchased coupon");

        }
    }

    @Override
    public void run(String... args) throws Exception {
        insert();
    }
}
