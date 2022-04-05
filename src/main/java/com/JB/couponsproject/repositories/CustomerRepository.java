package com.JB.couponsproject.repositories;

import com.JB.couponsproject.entities.CouponEntity;
import com.JB.couponsproject.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<CustomerEntity,Long> {

    boolean existsByEmailAndPassword(String email,String password);
}
