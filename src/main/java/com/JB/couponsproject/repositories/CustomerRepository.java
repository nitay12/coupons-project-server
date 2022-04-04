package com.JB.couponsproject.repositories;

import com.JB.couponsproject.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity,Long> {
}
