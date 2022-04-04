package com.JB.couponsproject.dto;

import com.JB.couponsproject.enums.Category;

import java.time.LocalDate;

/**
 * A data transfer object for the coupon entity
 */
public class CouponDto {
    private long id;
    private long companyID;
    private Category category;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private int amount;
    private double price;
    private String image;

}
