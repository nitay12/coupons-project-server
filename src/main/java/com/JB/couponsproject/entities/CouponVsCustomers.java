package com.JB.couponsproject.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
@Data
@NoArgsConstructor
public class CouponVsCustomers implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name="coupon_iD",referencedColumnName = "id", nullable = false)
    public CouponEntity coupon;

    @Id
    @ManyToOne
    @JoinColumn(name="customer_id",referencedColumnName = "id",nullable = false )
    public CustomerEntity customer;
}
