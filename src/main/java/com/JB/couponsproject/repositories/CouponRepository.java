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

    @Query(value = "SELECT * FROM coupons AS c JOIN coupon_vs_customers AS cc ON cc.coupon_id = c.id WHERE " +
                   "cc.customer_id = ?1", nativeQuery = true)
    List<CouponEntity> getCustomerCoupons(long customerID);

    @Query(value = "SELECT * FROM coupons AS c JOIN coupon_vs_customers AS cc" +
                   " ON c.id = cc.coupon_id WHERE cc.customer_id = ?1 AND c.category = ?2", nativeQuery = true)
    List<CouponEntity> getCustomerCouponsByCategory(long customerID, Category category);

    @Query(value = "SELECT * FROM coupons AS c JOIN coupon_vs_customers AS cc" +
                   " ON c.id = cc.coupon_id WHERE cc.customer_id = ?1 And c.price <= ?2", nativeQuery = true)
    List<CouponEntity> findCustomerCouponsByPriceLessThan(long customerID, double maxPrice);

    List<CouponEntity> getByCompanyId(long companyID);

    List<CouponEntity> getByCompanyIdAndCategory(long companyID, Category category);

    @Query(value = "UPDATE coupons SET category = ?1,title = ?2,description = ?3,start_date = ?4,end_date = ?5, amount = ?6," +
            "price = ?7,image = ?8",nativeQuery = true)
    CouponEntity updateCoupon(Category category, String title, String description, LocalDate starDate,
                               LocalDate endDate,int amount,double price,String image);

}
