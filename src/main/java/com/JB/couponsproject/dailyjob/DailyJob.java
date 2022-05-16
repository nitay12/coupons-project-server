package com.JB.couponsproject.dailyjob;

import com.JB.couponsproject.services.DailyJobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DailyJob {

    private final DailyJobService dailyJobService;
    @Scheduled(fixedDelay = 100000L,fixedRate = 100000L)
    public void checkExpiredCoupons(){
        log.info("Starting expired coupons deletion");
        dailyJobService.check();
        log.info("Finished expired deletion");
    }
}
