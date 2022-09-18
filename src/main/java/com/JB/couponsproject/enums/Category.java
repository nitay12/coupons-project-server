package com.JB.couponsproject.enums;

import com.JB.couponsproject.dto.CouponDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

import java.util.stream.Stream;

/**
 * Represents the coupon categories
 * Used in the Creation of coupons and when filtering coupons results
 */

@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Category {
    FOOD("FOOD"),
    ELECTRICITY("ELECTRICITY"),
    RESTAURANT("RESTAURANT"),
    VACATION("VACATION");

    private String str;

    @JsonCreator
    public static Category decode(final String check) {
        return Stream.of(Category.values()).filter(targetEnum -> targetEnum.str.equals(check)).findFirst().orElse(null);
    }

    @JsonValue
    public String getStr() {
        return str;
    }

    @Override
    public String toString() {
        return str;
    }
}
