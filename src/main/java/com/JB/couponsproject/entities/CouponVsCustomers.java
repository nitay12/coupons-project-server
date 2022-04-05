package com.JB.couponsproject.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
@NoArgsConstructor
@ToString
public class CouponVsCustomers implements Serializable {
//TODO: Delete the id and find a better solution to save a coupon purchase
    public CouponVsCustomers(CouponEntity coupon, CustomerEntity customer) {
        this.coupon = coupon;
        this.customer = customer;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name="coupon_iD",referencedColumnName = "id", nullable = false)
    public CouponEntity coupon;

    @ManyToOne
    @JoinColumn(name="customer_id",referencedColumnName = "id",nullable = false )
    public CustomerEntity customer;

    public CouponEntity getCoupon() {
        return coupon;
    }

    public void setCoupon(CouponEntity coupon) {
        this.coupon = coupon;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }
}
