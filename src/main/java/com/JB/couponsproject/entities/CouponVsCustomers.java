package com.JB.couponsproject.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@Table(name="coupons_vs_customers")
@AllArgsConstructor
@NoArgsConstructor
@IdClass(CouponCustomerID.class)
public class CouponVsCustomers {
    @Id
    private Long customerId;
    @Id
    private Long couponId;

    public CouponVsCustomers(CouponCustomerID couponCustomerID) {
        this.customerId = couponCustomerID.getCustomerId();
        this.couponId = couponCustomerID.getCouponId();
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }
}
