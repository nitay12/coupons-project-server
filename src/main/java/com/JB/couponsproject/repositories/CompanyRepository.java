package com.JB.couponsproject.repositories;

import com.JB.couponsproject.entities.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
    boolean existsByName(String name);

    boolean existsByEmail(String email);

    List<CompanyEntity> findByEmail(String email);

    List<CompanyEntity> findByName(String name);
}
