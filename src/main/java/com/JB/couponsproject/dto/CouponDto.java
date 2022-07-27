package com.JB.couponsproject.dto;

import com.JB.couponsproject.entities.CouponEntity;
import com.JB.couponsproject.entities.CustomerEntity;
import com.JB.couponsproject.enums.Category;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @Builder.Default
    private List<CustomerDto> buyers = new ArrayList<>();

    public CouponEntity toEntity() {
        return CouponEntity.builder()
                .id(this.id)
                .companyId(this.companyId)
                .category(this.category)
                .title(this.title)
                .description(this.description)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .amount(this.amount)
                .price(this.price)
                .image(this.image)
                .buyers(this.buyers
                        .stream()
                        .map(CustomerDto::toEntity)
                        .collect(Collectors.toList()))
                .build();
    }
}
