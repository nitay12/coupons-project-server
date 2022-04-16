package com.JB.couponsproject.dto;

import com.JB.couponsproject.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * A data transfer object for the coupon entity
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponDto {
    private Long id;
    private Long companyId;
    private Category category;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private int amount;
    private double price;
    private String image;

    public CouponDto(Category category, String title, String description, LocalDate startDate,LocalDate endDate, int amount, double price, String image) {
        this.category = category;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }
}
