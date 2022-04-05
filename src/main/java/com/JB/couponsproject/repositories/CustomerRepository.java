package com.JB.couponsproject.repositories;

import com.JB.couponsproject.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<CustomerEntity,Long> {

    CustomerEntity getDetailById(long customerId);

    CustomerEntity getDetailByEmail(String email);

    boolean existsByEmailAndPassword(String email,String password);

    boolean existsByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE customers SET first_name = :firstname, last_name = :lastname, email = :email, password = :password WHERE id = :id")
    public void updateCustomer(@Param("firstname") String firstname, @Param("lastname") String lastname, @Param("email") String email, @Param("password") int password, @Param("id") long id);

    void deleteByID(long customerId);



}
