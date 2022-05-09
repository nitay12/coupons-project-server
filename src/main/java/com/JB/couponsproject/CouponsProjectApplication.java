package com.JB.couponsproject;

import com.JB.couponsproject.dailyjob.DailyJob;
import com.JB.couponsproject.dto.CompanyDto;
import com.JB.couponsproject.dto.CouponDto;
import com.JB.couponsproject.entities.CompanyEntity;
import com.JB.couponsproject.entities.CouponEntity;
import com.JB.couponsproject.enums.Category;
import com.JB.couponsproject.exceptions.ApplicationException;
import com.JB.couponsproject.repositories.CouponRepository;
import com.JB.couponsproject.services.AdminService;
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
	}
}
