package com.JB.couponsproject.entities;

import com.JB.couponsproject.enums.Category;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@ToString
@AllArgsConstructor
@Entity
@Table(name = "coupons")
@NoArgsConstructor
public class CouponEntity implements Serializable {

    public CouponEntity(Long companyId, Category category, String title, String description, LocalDate startDate, LocalDate endDate, int amount, double price, String image) {
        this.companyId = companyId;
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
    private Long id;

    @Column(name = "company_id")
    private Long companyId;
    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;
    @Column(name="title", nullable = false)
    private String title;
    @Column(name="description", nullable = false)
    private String description;
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Positive
    @Column(name = "amount", nullable = false)
    private int amount;
    @Positive
    @Column(name = "price", nullable = false)

    private double price;
    @Column(name = "image")
    private String image;
    @ManyToMany(mappedBy = "coupons", cascade = {CascadeType.REMOVE})
    private List<CustomerEntity> buyers;

    public CouponEntity(Category category, String title, String description, LocalDate startDate, LocalDate endDate, int amount, double price, String image) {
        this.category = category;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }

}
