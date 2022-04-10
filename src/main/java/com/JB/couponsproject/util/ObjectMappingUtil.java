package com.JB.couponsproject.util;

import com.JB.couponsproject.dto.CouponDto;
import com.JB.couponsproject.entities.CouponEntity;

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
}
