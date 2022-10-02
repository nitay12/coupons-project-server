package com.JB.couponsproject.services;

import com.JB.couponsproject.constants.TestData;
import com.JB.couponsproject.entities.CompanyEntity;
import com.JB.couponsproject.entities.CustomerEntity;
import com.JB.couponsproject.repositories.CompanyRepository;
import com.JB.couponsproject.repositories.CustomerRepository;
import com.JB.couponsproject.util.ObjectMappingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ProjUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;
    private final CompanyRepository companyRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (TestData.ADMIN_LOGIN_EMAIL.equals(email)){
            return new org.springframework.security.core.userdetails.User(
                    TestData.ADMIN_LOGIN_EMAIL,
                    TestData.AMDIN_LOGIN_PASSWORD,
                    new ArrayList<>()
            );
        }
        else if (customerRepository.existsByEmail(email)){
            CustomerEntity customer = customerRepository.findByEmail(email).get(0);
            return ObjectMappingUtil.CustomerToUserDetails(customer);

        }
        else if (companyRepository.existsByEmail(email)){
            CompanyEntity company = companyRepository.findByEmail(email).get(0);
            return ObjectMappingUtil.CompanyToUserDetails(company);
        }
        else {
            return null;
        }
    }

}
