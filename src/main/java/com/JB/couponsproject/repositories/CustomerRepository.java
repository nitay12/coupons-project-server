package com.JB.couponsproject.repositories;

import com.JB.couponsproject.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity,Long> {

    CustomerEntity findByEmail(String email);

    boolean existsByEmailAndPassword(String email,String password);
    boolean existsByCouponsId(long id);
    boolean existsByEmail(String email);

//    @Modifying(clearAutomatically = true)
//    @Query("UPDATE customers SET first_name = :firstname, last_name = :lastname, email = :email, password = :password WHERE id = :id")
//    public void updateCustomer(@Param("firstname") String firstname, @Param("lastname") String lastname, @Param("email") String email, @Param("password") int password, @Param("id") long id);


}
