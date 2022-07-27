package com.JB.couponsproject.dto;

import com.JB.couponsproject.entities.CouponEntity;
import com.JB.couponsproject.entities.CustomerEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A data transfer object for the customer entity
 */
@Builder
@AllArgsConstructor
@ToString
@Data
@RequiredArgsConstructor
public class CustomerDto {
    private final long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @Builder.Default private List<CouponDto> coupons = new ArrayList<>();

    public CustomerEntity toEntity() {
        return CustomerEntity.builder()
                .id(this.id)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .email(this.email)
                .password(this.password)
                .coupons(this.coupons
                        .stream()
                        .map(CouponDto::toEntity)
                        .collect(Collectors.toList()))
                .build();
    }

    public void hashPassword() {
        setPassword(String.valueOf(password.hashCode()));
    }
}
