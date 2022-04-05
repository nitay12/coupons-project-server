package com.JB.couponsproject.repositories;
import com.JB.couponsproject.entities.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<CompanyEntity,Long> {

    boolean existsByEmailAndPassword(String email,String password);

}
