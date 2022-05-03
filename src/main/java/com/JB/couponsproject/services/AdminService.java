package com.JB.couponsproject.services;

import com.JB.couponsproject.dto.CustomerDto;
import com.JB.couponsproject.entities.CompanyEntity;
import com.JB.couponsproject.entities.CustomerEntity;
import com.JB.couponsproject.exceptions.ApplicationException;
import com.JB.couponsproject.exceptions.WrongCertificationsException;
import com.JB.couponsproject.repositories.CompanyRepository;
import com.JB.couponsproject.repositories.CouponRepository;
import com.JB.couponsproject.repositories.CustomerRepository;
import com.JB.couponsproject.texts.Texts;
import com.JB.couponsproject.util.ObjectMappingUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final CompanyRepository companyRepository;
    private final CouponRepository couponRepository;
    private final CustomerRepository customerRepository;
    Logger logger = LoggerFactory.getLogger(AdminService.class);

    public void login(String email, String password) throws ApplicationException {
        if (email.equals(Texts.ADMIN_EMAIL) & password.equals(Texts.AMDIN_PASSWORD)) {
            logger.info("Administrator logged into the system.");
            return;
        }
        throw new WrongCertificationsException("Wrong email or password");
    }

    public CustomerDto create(final CustomerDto customerDto) throws ApplicationException {
        if(!customerRepository.existsByEmail(customerDto.getEmail())){
            final CustomerEntity customerEntity = ObjectMappingUtil.customerDtoToEntity(customerDto);
            return ObjectMappingUtil.customerEntityToDto(customerRepository.save(customerEntity));
        }
        else {
            throw new ApplicationException("Email already exist in system.");
        }
    }






}
