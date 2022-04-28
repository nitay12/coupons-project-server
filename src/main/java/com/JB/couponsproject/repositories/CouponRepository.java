package com.JB.couponsproject.repositories;

import com.JB.couponsproject.entities.CouponEntity;
import com.JB.couponsproject.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<CouponEntity,Long> {

    List<CouponEntity> findByCategory(Category category);

    List<CouponEntity> findByPriceLessThan(double maxPrice);

    @Query(value = "SELECT * FROM coupons AS c JOIN coupon_vs_customer AS cc ON cc.coupon_id = c.id WHERE " +
                   "cc.customer_id = ?1", nativeQuery = true)
    List<CouponEntity> getCustomerCoupons(long customerID);

    @Query(value = "SELECT * FROM coupons AS c JOIN coupon_vs_customer AS cc" +
                   " ON c.id = cc.coupon_id WHERE cc.customer_id = ?1", nativeQuery = true)
    List<CouponEntity> getCustomerCouponsByCategory(long customerID, Category category);

    @Query(value = "SELECT * FROM coupons AS c JOIN coupon_vs_customer AS cc" +
                   " ON c.id = cc.coupon_id WHERE cc.customer_id = ?1", nativeQuery = true)
    List<CouponEntity> findCustomerCouponsByPriceLessThan(long customerID, double maxPrice);

    List<CouponEntity> getByCompanyId(long companyID);

    List<CouponEntity> getByCompanyIdAndCategory(long companyID, Category category);

    @Query(value = "SELECT * FROM coupons AS c WHERE end_date < CURDATE()",nativeQuery = true)
    List<CouponEntity> getExpiredCoupons();
}
