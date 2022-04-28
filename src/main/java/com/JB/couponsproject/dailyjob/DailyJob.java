package com.JB.couponsproject.dailyjob;

import com.JB.couponsproject.services.DailyJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DailyJob {

    private final DailyJobService dailyJobService;

    @Scheduled(fixedDelay = 100000L,fixedRate = 100000L)
    public void checkExpiredCoupons(){
        dailyJobService.check();
    }
}
