package com.JB.couponsproject.util;

import com.JB.couponsproject.dto.CouponDto;
import com.JB.couponsproject.dto.CustomerDto;
import com.JB.couponsproject.entities.CouponEntity;
import com.JB.couponsproject.entities.CustomerEntity;

public class ObjectMappingUtil {
    public static CouponEntity couponDtoToEntity(CouponDto couponDto){
        return new CouponEntity(
                couponDto.getCategory(),
                couponDto.getTitle(),
                couponDto.getDescription(),
                couponDto.getStartDate(),
                couponDto.getEndDate(),
                couponDto.getAmount(),
                couponDto.getPrice(),
                couponDto.getImage()
        );
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


}
