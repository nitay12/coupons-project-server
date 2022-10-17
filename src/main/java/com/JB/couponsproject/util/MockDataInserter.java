package com.JB.couponsproject.util;

import com.JB.couponsproject.dto.CompanyDto;
import com.JB.couponsproject.dto.CouponDto;
import com.JB.couponsproject.dto.CustomerDto;
import com.JB.couponsproject.entities.CouponEntity;
import com.JB.couponsproject.enums.Category;
import com.JB.couponsproject.exceptions.ApplicationException;
import com.JB.couponsproject.repositories.CouponRepository;
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
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
@Order(1)
public class MockDataInserter implements CommandLineRunner {
    private final AdminService adminService;
    private final CustomerService customerService;
    private final CompanyService companyService;
    private final CouponRepository couponRepository;
    private final Logger logger = LoggerFactory.getLogger(MockDataInserter.class);

    private static final Random random = new Random();

    public static Category getRandomCategory(){
        Category[] categories = Category.values();
        return categories[random.nextInt(categories.length)];
    }

    public void insert() throws ApplicationException {
        logger.info("Inserting mock data to the DB");
        for (int i = 1; i <= 50; i++) {
            final CompanyDto newCompany = adminService.createCompany
                    (CompanyDto.builder()
                            .name("company" + i)
                            .email("company" + i + "@email.com")
                            .password("123456")
                            .build());
            logger.debug("New company added:" + newCompany.toString());
            companyService.login(
                    newCompany.getEmail(), "123456"
            );
            int r = random.nextInt(5)+1;
            for (int n = 0; n <= r; n++) {
                long l = i;
                final CouponEntity coupon = companyService.addCoupon(
                        CouponDto.builder()
                                .category(getRandomCategory())
                                .title("title" + i+", "+n)
                                .description("desc "+i+", "+n)
                                .companyId(l)
                                .startDate(LocalDate.of(2022, 2, 2))
                                .endDate(LocalDate.of(2023, 5, 10))
                                .amount(300)
                                .price(random.nextInt(300) + 50)
                                .image("https://company/image.jpg")
                                .build()
                );
            }
        }
        List<CouponEntity> couponEntities = couponRepository.findAll();
        for (int i = 1; i <= 50; i++) {
            final CustomerDto newCustomer = adminService.createCustomer(
                    CustomerDto.builder()
                            .firstName("customer" + i)
                            .lastName("last name")
                            .email("customer" + i + "@email.com")
                            .password("123456").build());
            logger.debug("New customer was added to the DB: " + newCustomer.toString());
            int r = random.nextInt(5)+1;
            for (int n = 0; n <= r; n++) {
                customerService.purchaseCoupon(random.nextLong(couponEntities.size())+1, newCustomer.getId());
            }
            logger.debug(newCustomer.getFirstName() + " purchased coupon");
        }
    }

    @Override
    public void run(String... args) throws Exception {
        insert();
    }
}
