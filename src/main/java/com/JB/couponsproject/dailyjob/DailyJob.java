package com.JB.couponsproject.dailyjob;

import com.JB.couponsproject.services.DailyJobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@RequiredArgsConstructor
@Order(5)
@Slf4j
public class DailyJob implements CommandLineRunner {
    private final DailyJobService dailyJobService;

    @Override
    public void run(String... args) {
        log.info("Enabling expired coupons daily deletion");
        dailyJobService.check();
    }
}
