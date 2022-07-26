package com.JB.couponsproject;

import com.JB.couponsproject.dailyjob.DailyJob;
import com.JB.couponsproject.system.CouponSystem;
import com.JB.couponsproject.tests.AdminServiceTest;
import com.JB.couponsproject.tests.CompanyServiceTest;
import com.JB.couponsproject.tests.CustomerServiceTest;
import com.JB.couponsproject.util.MockDataInserter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class CouponsProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(CouponsProjectApplication.class, args);
    }
}
