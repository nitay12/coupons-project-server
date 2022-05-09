package com.JB.couponsproject.tests;

import com.JB.couponsproject.dto.CouponDto;
import com.JB.couponsproject.entities.CouponEntity;
import com.JB.couponsproject.enums.Category;
import com.JB.couponsproject.exceptions.ApplicationException;
import com.JB.couponsproject.repositories.CouponRepository;
import com.JB.couponsproject.services.CompanyService;
import com.JB.couponsproject.util.ObjectMappingUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@RequiredArgsConstructor
@Component
@Order(3)
public class CompanyServiceTest implements CommandLineRunner {
    private final CompanyService companyService;
    private final CouponRepository couponRepository;
    private final Logger logger = LoggerFactory.getLogger(CompanyServiceTest.class);

    @Override
    public void run(String... args){
        try {
            //Tests
            //Login test
            companyService.login("company2@email.com", "123456");
            logger.info("WELCOME "+ companyService.getLoggedInCompany().getName().toUpperCase());
            //Add coupon test
            logger.info("Adding coupon...");
            final CouponDto testCouponDto = new CouponDto(
                    Category.ELECTRICITY,
                    "test title",
                    "test desc",
                    LocalDate.now(),
                    LocalDate.now(),
                    10,
                    10,
                    "url"
            );
            Long newCouponId = companyService.addCoupon(testCouponDto);
            logger.info("Coupon added (id:"+newCouponId+")");
            CouponEntity newCoupon = couponRepository.findById(newCouponId).get();
            //Update coupon test
            logger.info("Updating coupon...");
            newCoupon.setDescription("updated desc");
            final CouponDto newCouponDto = ObjectMappingUtil.couponEntityToCouponDto(newCoupon);
            companyService.updateCoupon(newCouponDto);
            logger.info("Updated coupon:");
            logger.info(couponRepository.findById(newCouponId).get().toString());
            // Delete coupon test
            logger.info("Deleting coupon...");
            companyService.deleteCoupon(2L);
            logger.info("Coupon deleted");
            //Get company coupons test
            logger.info("All company coupons");
            logger.info(companyService.getCompanyCoupons().toString());
            logger.info("All company coupons from category (ELECTRICITY)");
            logger.info(companyService.getCompanyCoupons(Category.ELECTRICITY).toString());
            logger.info("All company coupons up to 500 (max price)");
            logger.info(companyService.getCompanyCoupons(500d).toString());
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }
}
