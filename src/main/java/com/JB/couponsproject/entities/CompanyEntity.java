package com.JB.couponsproject.entities;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "companies")
@NoArgsConstructor
@ToString
public class CompanyEntity implements Serializable {

    public CompanyEntity(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password.hashCode();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private int password;

    //TODO: Check if the coupons list is needed
//    @OneToMany(fetch = FetchType.EAGER, mappedBy = "companyEntity")
//    private List<CouponEntity> coupons;

    //    public List<CouponEntity> getCoupons() {
//        return coupons;
//    }
//
//    public void setCoupons(List<CouponEntity> coupons) {
//        this.coupons = coupons;
//    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

}
