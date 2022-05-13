package com.JB.couponsproject.repositories;

import com.JB.couponsproject.dto.CompanyDto;
import com.JB.couponsproject.entities.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity,Long> {
    boolean existsByEmailAndPassword(String email,String password);

    boolean existsByName(String name);

    boolean existsByEmail(String email);

    List<CompanyEntity> findAllById(long id);

    List<CompanyDto> findByEmail(String email);

    List<CompanyDto> findByName(String name);
}
