package com.JB.couponsproject.repositories;

import com.JB.couponsproject.entities.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface CompanyRepository extends JpaRepository<CompanyEntity,Long> {

    CompanyEntity getDetailById(long customerId);

    CompanyEntity getDetailByEmail(String email);

    boolean existsByEmailAndPassword(String email,String password);

    boolean existsByEmail(String email);

    boolean existsByName(String name);

//    ArrayList<CompanyEntity> getCompaniesArrayList();
//
//    @Modifying(clearAutomatically = true)
//    @Query("UPDATE companies SET password = :password WHERE id = :companyId")
//    public void updateCompany(@Param("password") int password, @Param("companyId") int companyId);

}
