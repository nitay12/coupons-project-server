package com.JB.couponsproject.repositories;

import com.JB.couponsproject.entities.CouponEntity;
import com.JB.couponsproject.entities.CouponVsCustomers;
import com.JB.couponsproject.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponVsCustomersRepository extends JpaRepository<CouponVsCustomers, Long> {
}
