package com.JB.couponsproject.services;

import com.JB.couponsproject.entities.CouponEntity;
import com.JB.couponsproject.repositories.CouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
@Slf4j
public class DailyJobService {

    private final CouponRepository couponRepository;

    public void check(){
        List<CouponEntity> expiredCoupons = couponRepository.getExpiredCoupons();
        for (CouponEntity expiredCoupon : expiredCoupons) {
            couponRepository.deleteById(expiredCoupon.getId());
            log.info("deleted coupon" + expiredCoupon.getId());
        }
    }
    public void stop(){
        Thread.interrupted();
    }
}
