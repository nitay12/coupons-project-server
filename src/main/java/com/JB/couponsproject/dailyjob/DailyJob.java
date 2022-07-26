package com.JB.couponsproject.dailyjob;

import com.JB.couponsproject.services.DailyJobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(5)
@Slf4j
public class DailyJob implements CommandLineRunner {
    private final DailyJobService dailyJobService;

    @Override
    @Scheduled(fixedDelay = 100000L, fixedRate = 100000L)
    public void run(String... args) {
        log.info("Starting expired coupons deletion");
        dailyJobService.check();
        log.info("Finished expired deletion");
    }
}
