package com.JB.couponsproject.dto;

import com.JB.couponsproject.enums.Category;
import lombok.*;

import java.time.LocalDate;

/**
 * A data transfer object for the coupon entity
 */
@Builder
@Data
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class CouponDto {
    private final Long id;
    private Long companyId;
    private Category category;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private int amount;
    private double price;
    private String image;

}
