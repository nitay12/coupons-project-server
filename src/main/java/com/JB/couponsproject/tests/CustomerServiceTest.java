package com.JB.couponsproject.tests;

import com.JB.couponsproject.enums.Category;
import com.JB.couponsproject.exceptions.ApplicationException;
import com.JB.couponsproject.services.CompanyService;
import com.JB.couponsproject.services.CustomerService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(4)
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceTest implements CommandLineRunner{
    private final CustomerService customerService;
    @Override
    public void run(String... args) throws Exception {
        //Login tests
        //Login failed test
        try {
            customerService.login("customer2@email.com", "WRONG PASSWORD");
        } catch (ApplicationException e) {
            log.info("Login failed test, thrown exception: " + e.getMessage());
        }
        //Login succeed test
        log.info("Login succeed test");
        customerService.login("customer1@email.com", "123456");
        //Purchase coupon test
        log.info("Purchase coupon test");
        customerService.purchaseCoupon(9L,3L);
        //Get customer coupons tests (all, category, max price
        log.info("get all customer coupons:");
        log.info(customerService.getCustomerCoupons(3L).toString());
        log.info("get all customer coupons up to max price:");
        log.info(customerService.getCustomerCoupons(Category.ELECTRICITY, 3L).toString());
        log.info("get all customer coupons filtered by category:");
        log.info(customerService.getCustomerCoupons(150D, 3L).toString());
        log.info(customerService.getCustomerCoupons(3L).toString());
    }
}
