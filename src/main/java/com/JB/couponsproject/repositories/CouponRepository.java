package com.JB.couponsproject.repositories;

import com.JB.couponsproject.entities.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<CouponEntity,Long> {

}
