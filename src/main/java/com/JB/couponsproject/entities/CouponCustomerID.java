package com.JB.couponsproject.entities;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@NoArgsConstructor
@Data
@Embeddable
public class CouponCustomerID implements Serializable {
    private Long customerId;
    private Long couponId;

    public CouponCustomerID(Long customerId, Long couponId) {
        this.customerId = customerId;
        this.couponId = couponId;
    }
}

