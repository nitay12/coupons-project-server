package com.JB.couponsproject;

import com.JB.couponsproject.dto.CouponDto;
import com.JB.couponsproject.entities.CouponEntity;
import com.JB.couponsproject.enums.Category;
import com.JB.couponsproject.exceptions.ApplicationException;
import com.JB.couponsproject.repositories.CouponRepository;
import com.JB.couponsproject.services.CompanyService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;

@SpringBootApplication
public class CouponsProjectApplication {

	public static void main(String[] args) throws ApplicationException {
		ApplicationContext ctx = SpringApplication.run(CouponsProjectApplication.class, args);
		final CompanyService companyService = ctx.getBean(CompanyService.class);
		final CouponRepository couponRepository = ctx.getBean(CouponRepository.class);
		try {
			//Tests
			//Login test
			companyService.login("company2@email.com", "123456");
			//Add coupon test
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
			CouponEntity newCoupon = couponRepository.getById(newCouponId);
			//Update coupon test
			testCouponDto.setId(newCouponId);
			testCouponDto.setDescription("updated desc");
			companyService.updateCoupon(testCouponDto);
			System.out.println(couponRepository.findById(newCouponId).get().toString());
			// Delete coupon test
			companyService.deleteCoupon(1L);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		
	}

}
