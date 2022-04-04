package com.JB.couponsproject.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table
@Data
@NoArgsConstructor
public class CouponVsCustomers {

    @ManyToOne
    @JoinColumn(name="couponID",referencedColumnName = "id", nullable = false)
    public CouponEntity coupon;

    @ManyToOne
    @JoinColumn(name="customerId",referencedColumnName = "id",nullable = false )
    public CustomerEntity customer;
}
