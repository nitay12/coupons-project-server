package com.JB.couponsproject.services;

import com.JB.couponsproject.entities.CouponEntity;
import com.JB.couponsproject.entities.CustomerEntity;
import com.JB.couponsproject.repositories.CouponRepository;
import com.JB.couponsproject.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;


@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class DailyJobService {

    private final CouponRepository couponRepository;
    private final CustomerRepository customerRepository;
    @Scheduled(fixedRate = 300000L)
    public void check() {
        log.info("Starting expired coupons daily deletion");
        List<CouponEntity> expiredCoupons = couponRepository.getExpiredCoupons();
        for (CouponEntity expiredCoupon : expiredCoupons) {
            final List<CustomerEntity> buyers = expiredCoupon.getBuyers();
            for (CustomerEntity customer : buyers) {
                customer.deleteCoupon(expiredCoupon);
                customerRepository.save(customer);
            }
            couponRepository.deleteById(expiredCoupon.getId());
            log.info("deleted coupon" + expiredCoupon.getId() + ", expired at:" + expiredCoupon.getEndDate());
        }
        log.info("Expired coupons daily deletion finished");
    }
}
