package com.JB.couponsproject.util;

import com.JB.couponsproject.dto.CompanyDto;
import com.JB.couponsproject.dto.CouponDto;
import com.JB.couponsproject.dto.CustomerDto;
import com.JB.couponsproject.entities.CompanyEntity;
import com.JB.couponsproject.entities.CouponEntity;
import com.JB.couponsproject.entities.CustomerEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

public class ObjectMappingUtil {
    public static CouponEntity couponDtoToEntity(CouponDto couponDto){
        return CouponEntity.builder()
                .id(couponDto.getId())
                .companyId(couponDto.getCompanyId())
                .category(couponDto.getCategory())
                .title(couponDto.getTitle())
                .description(couponDto.getDescription())
                .startDate(couponDto.getStartDate())
                .endDate(couponDto.getEndDate())
                .amount(couponDto.getAmount())
                .price(couponDto.getPrice())
                .image(couponDto.getImage())
                .build();
    }
    
    public static CouponDto couponEntityToCouponDto(CouponEntity couponEntity){
        return CouponDto.builder()
                .id(couponEntity.getId())
                .companyId(couponEntity.getCompanyId())
                .category(couponEntity.getCategory())
                .title(couponEntity.getTitle())
                .description(couponEntity.getDescription())
                .startDate(couponEntity.getStartDate())
                .endDate(couponEntity.getEndDate())
                .amount(couponEntity.getAmount())
                .price(couponEntity.getPrice())
                .image(couponEntity.getImage())
                .build();
    }
    //Convert Customer Dto to Customer Entity using Builder
    public static CustomerEntity customerDtoToEntity(CustomerDto customerDto){
        return CustomerEntity.builder()
                .id(customerDto.getId())
                .email(customerDto.getEmail())
                .password(customerDto.getPassword())
                .firstName(customerDto.getFirstName())
                .lastName(customerDto.getLastName())
                .build();
    }
    //Convert Customer Entity to Customer Dto using Builder
    public static CustomerDto customerEntityToDto(CustomerEntity customerEntity){
        return CustomerDto.builder()
                .id(customerEntity.getId())
                .email(customerEntity.getEmail())
                .password(customerEntity.getPassword())
                .firstName(customerEntity.getFirstName())
                .lastName(customerEntity.getLastName())
                .build();
    }
    
    public static CompanyDto companyEntityToCompanyDto(CompanyEntity companyEntity){
        return CompanyDto.builder()
                .id(companyEntity.getId())
                .name(companyEntity.getName())
                .email(companyEntity.getEmail())
                .password(companyEntity.getPassword())
                .build();
    }
    public static CompanyEntity companyDtoToCompanyEntity(CompanyDto companyDto){
        return CompanyEntity.builder()
                .id(companyDto.getId())
                .name(companyDto.getName())
                .email(companyDto.getEmail())
                .password(companyDto.getPassword())
                .build();
    }

    public static UserDetails CustomerToUserDetails(CustomerEntity customer){
        return new org.springframework.security.core.userdetails.User(
                customer.getEmail(),
                customer.getPassword(),
                new ArrayList<>()
        );
    }

    public static UserDetails CompanyToUserDetails(CompanyEntity company){
        return new org.springframework.security.core.userdetails.User(
                company.getEmail(),
                company.getPassword(),
                new ArrayList<>()
        );
    }


}
