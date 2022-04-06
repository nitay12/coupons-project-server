package com.JB.couponsproject.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@Table(name = "coupons_vs_customers")
@AllArgsConstructor
@NoArgsConstructor
public class CouponVsCustomers {
    @ManyToOne
    @JoinColumn(name = "couponID", referencedColumnName = "id", nullable = false)
    private Long customerId;
    @ManyToOne
    @JoinColumn(name = "customerId", referencedColumnName = "id", nullable = false)
    private Long couponId;

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
