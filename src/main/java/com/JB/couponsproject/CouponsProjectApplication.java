package com.JB.couponsproject;

import com.JB.couponsproject.dailyjob.DailyJob;
import com.JB.couponsproject.dto.CouponDto;
import com.JB.couponsproject.entities.CompanyEntity;
import com.JB.couponsproject.entities.CouponEntity;
import com.JB.couponsproject.enums.Category;
import com.JB.couponsproject.exceptions.ApplicationException;
import com.JB.couponsproject.repositories.CouponRepository;
import com.JB.couponsproject.services.CompanyService;
import com.JB.couponsproject.util.ObjectMappingUtil;
import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;

@SpringBootApplication
public class CouponsProjectApplication {

	public static void main(String[] args) throws ApplicationException {
		ApplicationContext ctx = SpringApplication.run(CouponsProjectApplication.class, args);
		DailyJob dailyJob = ctx.getBean(DailyJob.class);
		dailyJob.checkExpiredCoupons();
		final CompanyService companyService = ctx.getBean(CompanyService.class);
		final CouponRepository couponRepository = ctx.getBean(CouponRepository.class);
		try {
			//Tests
			//Login test
			companyService.login("company2@email.com", "123456");
			System.out.println("WELCOME "+ companyService.getLoggedInCompany().getName().toUpperCase());
			//Add coupon test
			System.out.println("Adding coupon...");
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
			System.out.println("Coupon added (id:"+newCouponId+")");
			CouponEntity newCoupon = couponRepository.findById(newCouponId).get();
			//Update coupon test
			System.out.println("Updating coupon...");
			newCoupon.setDescription("updated desc");
			final CouponDto newCouponDto = ObjectMappingUtil.couponEntityToCouponDto(newCoupon);
			companyService.updateCoupon(newCouponDto);
			System.out.println("Updated coupon:");
			System.out.println(couponRepository.findById(newCouponId).get().toString());
			// Delete coupon test
			System.out.println("Deleting coupon...");
			companyService.deleteCoupon(2L);
			System.out.println("Coupon deleted");
			//Get company coupons test
			System.out.println("All company coupons");
			System.out.println(companyService.getCompanyCoupons());
			System.out.println("All company coupons from category (ELECTRICITY)");
			System.out.println(companyService.getCompanyCoupons(Category.ELECTRICITY));
			System.out.println("All company coupons up to 500 (max price)");
			System.out.println(companyService.getCompanyCoupons(500d));
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
}
