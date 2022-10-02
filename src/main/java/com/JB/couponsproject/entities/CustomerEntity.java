package com.JB.couponsproject.entities;

import com.JB.couponsproject.dto.CustomerDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "customers")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CustomerEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;
    @Column(name = "first_name", nullable = false)
    @Getter @Setter
    private String firstName;
    @Column(name = "last_name", nullable = false)
    @Getter @Setter
    private String lastName;

    @Email
    @Column(name = "email", nullable = false, unique = true)
    @Getter @Setter
    private String email;
    @Column(name = "password", nullable = false)
    @JsonIgnore
    @Getter @Setter
    private String password;
    @Getter @Setter
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "coupon_vs_customer",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "coupon_id")
    )
    @Builder.Default
    private List<CouponEntity> coupons = new ArrayList<>();

    public void purchaseCoupon(CouponEntity coupon) {
        coupons.add(coupon);
    }
    public void deleteCoupon(CouponEntity coupon) {
        coupons.remove(coupon);
    }

    public CustomerDto toDto() {
        return CustomerDto.builder()
                .id(this.id)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .email(this.email)
                .password(this.password)
                .coupons(this.coupons
                        .stream()
                        .map(CouponEntity::toDto)
                        .collect(Collectors.toList()))
                .build();
    }
}
