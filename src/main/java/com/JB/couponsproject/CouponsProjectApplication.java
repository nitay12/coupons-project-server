package com.JB.couponsproject;

import com.JB.couponsproject.dto.CouponDto;
import com.JB.couponsproject.dto.CustomerDto;
import com.JB.couponsproject.entities.CustomerEntity;
import com.JB.couponsproject.enums.Category;
import com.JB.couponsproject.exceptions.ApplicationException;
import com.JB.couponsproject.repositories.CustomerRepository;
import com.JB.couponsproject.services.CompanyService;
import com.JB.couponsproject.services.CustomerService;
import com.JB.couponsproject.util.MockDataInserter;
import com.JB.couponsproject.util.ObjectMappingUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;

@SpringBootApplication
public class CouponsProjectApplication {

	public static void main(String[] args) throws ApplicationException {
		ApplicationContext ctx = SpringApplication.run(CouponsProjectApplication.class, args);
		final MockDataInserter mockDataInserter = ctx.getBean(MockDataInserter.class);
		mockDataInserter.insert();


		final CompanyService companyService = ctx.getBean(CompanyService.class);
		try {
			companyService.login("company2@email.com", "123456");
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
			System.out.println(companyService.addCoupon(testCouponDto));
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		
	}

}
