package com.JB.couponsproject.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "customers")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CustomerEntity {
    public CustomerEntity(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password.hashCode();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="first_name", nullable = false)
    private String firstName;
    @Column(name="last_name", nullable = false)
    private String lastName;
    @Column(name="email", nullable = false, unique = true)
    private String email;
    @Column(name="password", nullable = false)
    private int password;
    @Getter
    @ManyToMany(fetch = FetchType.EAGER , cascade =  { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name="coupon_vs_customer",
            joinColumns = @JoinColumn(name="customer_id"),
            inverseJoinColumns = @JoinColumn(name = "coupon_id")
    )
    private List<CouponEntity> coupons = new ArrayList<>();

    public void purchaseCoupon(CouponEntity coupon){
        coupons.add(coupon);
    }

}
