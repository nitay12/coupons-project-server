package com.JB.couponsproject.entities;

import com.JB.couponsproject.enums.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "coupon")
@Data
@NoArgsConstructor
public class CouponEntity {

    public CouponEntity(long companyID, Category category, String title, String description, LocalDate startDate, LocalDate endDate, int amount, double price, String image) {
        this.companyID = companyID;
        this.category = category;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="company_id", nullable = false)
    private long companyID;
    @Column(name="category", nullable = false)
    private Category category;
    @Column(name="title", nullable = false)
    private String title;
    @Column(name="description", nullable = false)
    private String description;
    @Column(name="start_date", nullable = false)
    private LocalDate startDate;
    @Column(name="end_date", nullable = false)
    private LocalDate endDate;
    @Column(name="amount", nullable = false)
    private int amount;
    @Column(name="price", nullable = false)
    private double price;
    @Column(name = "image")
    private String image;

}
