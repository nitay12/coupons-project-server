package com.JB.couponsproject.tests;

import com.JB.couponsproject.constants.TestData;
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
public class CustomerServiceTest implements CommandLineRunner {
    private final CustomerService customerService;

    @Override
    public void run(String... args) throws Exception {
        //Login tests
        //Login failed test
        try {
            try {
                customerService.login(TestData.CUSTOMER_LOGIN_EMAIL, TestData.LOGIN_WRONG_PASSWORD);
            } catch (ApplicationException e) {
                log.info(TestData.LOGIN_FAILED + e.getMessage());
            }
            //Login succeed test
            log.info("Login succeed test");
            try {
                customerService.login(TestData.CUSTOMER_LOGIN_EMAIL, TestData.CUSTOMER_LOGIN_PASSWORD);
            }
            catch (ApplicationException e){
                System.out.println("Wrong credentials");
            }
            //Purchase coupon test
            log.info("Purchase coupon test");
            customerService.purchaseCoupon(TestData.CUSTOMER_COUPON_ID, TestData.CUSTOMER_ID);
            //Get customer coupons tests (all, category, max price
            log.info("get all customer coupons:");
            log.info(customerService.getCustomerCoupons(TestData.CUSTOMER_ID).toString());
            log.info("get all customer coupons filtered by category:" + Category.ELECTRICITY);
            log.info(customerService.getCustomerCouponsByCategory(Category.ELECTRICITY, TestData.CUSTOMER_ID).toString());
            log.info("get all customer coupons up to max price: "+TestData.CUSTOMER_MAX_PRICE);
            log.info(customerService.getCustomerCoupons(TestData.CUSTOMER_MAX_PRICE, TestData.CUSTOMER_ID).toString());
            log.info("get all customer coupons:");
            log.info(customerService.getCustomerCoupons(TestData.CUSTOMER_ID).toString());
        } catch (ApplicationException e) {
            e.printStackTrace();
        }

    }
}
