package com.JB.couponsproject;

import com.JB.couponsproject.enums.Category;
import com.JB.couponsproject.repositories.CompanyRepository;

import com.JB.couponsproject.repositories.CouponRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CouponsProjectApplicationTests {
	@Autowired
	CompanyRepository companyRepository;
	@Autowired
	CouponRepository couponRepository;
	@Test
	void contextLoads() {
		System.out.println(companyRepository.findAll());
		System.out.println(couponRepository.findByCategory(Category.ELECTRICITY));
//		System.out.println(couponRepository.findByPriceLessThan(5.0));
		System.out.println(companyRepository.findById(1L));
		System.out.println(couponRepository.getCustomerCoupons(2L));
		System.out.println(couponRepository.getCustomerCouponsByCategory(1L,Category.FOOD));
		System.out.println(couponRepository.findCustomerCouponsByPriceLessThan(1L,400.0));
		System.out.println(couponRepository.getExpiredCoupons());
	}
}
