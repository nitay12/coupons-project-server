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
        ApplicationContext ctx = SpringApplication.run(CouponsProjectApplication.class, args);
        DailyJob dailyJob = ctx.getBean(DailyJob.class);
        dailyJob.checkExpiredCoupons();
        CouponSystem couponSystem = ctx.getBean(CouponSystem.class);
        MockDataInserter mockDataInserter = ctx.getBean(MockDataInserter.class);
        AdminServiceTest adminServiceTest = ctx.getBean(AdminServiceTest.class);
        CompanyServiceTest companyServiceTest = ctx.getBean(CompanyServiceTest.class);
        CustomerServiceTest customerServiceTest = ctx.getBean(CustomerServiceTest.class);
        try {
            couponSystem.run("123");
            mockDataInserter.run("123");
            adminServiceTest.run("123");
            companyServiceTest.run("123");
            customerServiceTest.run("123");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
  }
}
