package com.JB.couponsproject.repositories;

import com.JB.couponsproject.entities.CouponEntity;
import com.JB.couponsproject.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<CouponEntity, Long> {
    //Company methods
    boolean existsByIdAndCompanyId(Long id, Long companyId);
    boolean existsByTitleAndCompanyId(String title, Long companyId);
    List<CouponEntity> findByCompanyIdAndPriceLessThan(Long companyId, double maxPrice);
    List<CouponEntity> findByPriceLessThan(double maxPrice);
    List<CouponEntity> getByCompanyId(long companyID);
    List<CouponEntity> getByCategory(Category category);
    List<CouponEntity> getByCompanyIdAndCategory(long companyID, Category category);

    void deleteAllByCompanyId(long id);

    //Customer methods
    @Query(value = "SELECT * FROM coupons AS c JOIN coupon_vs_customer AS cc ON cc.coupon_id = c.id WHERE " +
                   "cc.customer_id = ?1", nativeQuery = true)
    List<CouponEntity> getCustomerCoupons(long customerID);

    @Query(value = "SELECT * FROM coupons AS c WHERE end_date < CURDATE()", nativeQuery = true)
    List<CouponEntity> getExpiredCoupons();

}
