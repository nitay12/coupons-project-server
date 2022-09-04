package com.JB.couponsproject.tests;

import com.JB.couponsproject.constants.TestData;
import com.JB.couponsproject.dto.CouponDto;
import com.JB.couponsproject.entities.CouponEntity;
import com.JB.couponsproject.enums.Category;
import com.JB.couponsproject.enums.UserType;
import com.JB.couponsproject.exceptions.ApplicationException;
import com.JB.couponsproject.login.LoginManager;
import com.JB.couponsproject.repositories.CouponRepository;
import com.JB.couponsproject.services.CompanyService;
import com.JB.couponsproject.util.ObjectMappingUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Order(3)
@RequiredArgsConstructor
@Component
@Transactional
public class CompanyServiceTest implements CommandLineRunner {
    private final CompanyService companyService;
    private final CouponRepository couponRepository;
    private final LoginManager loginManager;
    private final Logger logger = LoggerFactory.getLogger(CompanyServiceTest.class);

    @Override
    public void run(String... args) {
        try {
            //Tests
            //Login test
            try {
                loginManager.jwtLogin(UserType.COMPANY, TestData.COMPANY_LOGIN_EMAIL, TestData.LOGIN_WRONG_PASSWORD);
            } catch (ApplicationException e) {
                logger.info(TestData.LOGIN_FAILED + e.getMessage());
            }
            logger.info("Login succeed test");
            logger.info(loginManager.jwtLogin(UserType.COMPANY, TestData.COMPANY_LOGIN_EMAIL, TestData.COMPANY_LOGIN_PASSWORD).toString());
            //Add coupon test
            logger.info("Add coupon test");
            final CouponDto testCouponDto = CouponDto.builder()
                    .category(Category.ELECTRICITY)
                    .title(TestData.COUPON_TITLE)
                    .description(TestData.COUPON_DESCRIPTION)
                    .startDate(LocalDate.now())
                    .endDate(LocalDate.now())
                    .amount(TestData.COUPON_AMOUNT)
                    .price(TestData.COUPON_PRICE)
                    .image(TestData.COUPON_IMG)
                    .build();
            Long newCouponId = companyService.addCoupon(testCouponDto, 1L);
            logger.info("Coupon added (id:" + newCouponId + ")");
            try {
                companyService.addCoupon(testCouponDto, 1L);
            } catch (ApplicationException e) {
                logger.info("Add coupon with same title test, thrown exception: " + e.getMessage());
            }
            CouponEntity newCoupon = couponRepository.findById(newCouponId).get();
            //Update coupon test
            logger.info("Update coupon test");
            newCoupon.setDescription(TestData.COUPON_UPDATED_DESCRIPTION);
            final CouponDto newCouponDto = newCoupon.toDto();
            companyService.updateCoupon(newCouponDto, TestData.COMPANY_ID);
            logger.info("Updated coupon description:");
            logger.info(couponRepository.findById(newCouponId).get().toString());
            //Update coupon id test (throws exception)
            try {
//                CouponDto(1L, CouponDto.builder()
//                        .companyId(newCouponDto.getCompanyId())
//                        .category(newCouponDto.getCategory())
//                        .title(newCouponDto.getTitle())
//                        .description(newCouponDto.getDescription())
//                        .startDate(newCouponDto.getStartDate())
//                        .endDate(newCouponDto.getEndDate())
//                        .amount(newCouponDto.getAmount())
//                        .price(newCouponDto.getPrice())
//                        .image(newCouponDto.getImage())

                CouponDto couponToUpdate = CouponDto.builder()
                        .id(newCouponDto.getId())
                        .companyId(newCouponDto.getCompanyId())
                        .category(newCouponDto.getCategory())
                        .title(newCouponDto.getTitle())
                        .description(newCouponDto.getDescription())
                        .startDate(newCouponDto.getStartDate())
                        .endDate(newCouponDto.getEndDate())
                        .amount(newCouponDto.getAmount())
                        .price(newCouponDto.getPrice())
                        .image(newCouponDto.getImage())
                        .build();

                companyService.updateCoupon(couponToUpdate, TestData.COMPANY_ID);
            } catch (ApplicationException e) {
                logger.info("Update coupon id test, thrown exception: " + e.getMessage());
            }
            // Delete coupon test
            logger.info("Deleting coupon...");
            companyService.deleteCoupon(TestData.COUPON_ID, TestData.COMPANY_ID);
            logger.info("Coupon deleted");
            //Get company coupons test
            logger.info("All company coupons");
            logger.info(companyService.getCompanyCoupons(TestData.COMPANY_ID).toString());
            logger.info("All company coupons from category (ELECTRICITY)");
            logger.info(companyService.getCompanyCoupons(Category.ELECTRICITY, TestData.COMPANY_ID).toString());
            logger.info("All company coupons up to " + TestData.GET_COUPON_MAX_PRICE + " (max price)");
            logger.info(companyService.getCompanyCoupons(TestData.GET_COUPON_MAX_PRICE, TestData.COMPANY_ID).toString());
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }
}
