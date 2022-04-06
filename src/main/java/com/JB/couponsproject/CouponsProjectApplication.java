package com.JB.couponsproject;

import com.JB.couponsproject.util.MockDataInserter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class CouponsProjectApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(CouponsProjectApplication.class, args);
//		final MockDataInserter mockDataInserter = ctx.getBean(MockDataInserter.class);
//		mockDataInserter.insert();
	}

}
