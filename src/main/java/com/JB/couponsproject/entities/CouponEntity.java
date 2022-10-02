package com.JB.couponsproject.entities;

import com.JB.couponsproject.dto.CouponDto;
import com.JB.couponsproject.enums.Category;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@ToString
@Table(name = "coupons")
public class CouponEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Column(name = "company_id")
    @Getter
    @Setter
    private Long companyId;
    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private Category category;
    @Column(name = "title", nullable = false)
    @Getter
    @Setter
    private String title;
    @Column(name = "description", nullable = false)
    @Getter
    @Setter
    private String description;
    @Column(name = "start_date", nullable = false)
    @Getter
    @Setter
    private LocalDate startDate;
    @Column(name = "end_date", nullable = false)
    @Getter
    @Setter
    private LocalDate endDate;

    @Positive
    @Column(name = "amount", nullable = false)
    @Getter
    @Setter
    private int amount;
    @Positive
    @Column(name = "price", nullable = false)
    @Getter
    @Setter
    private double price;
    @Column(name = "image")
    @Getter
    @Setter
    private String image;
    @ToString.Exclude @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(
            name = "coupon_vs_customer",
            joinColumns = @JoinColumn(name = "coupon_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    private List<CustomerEntity> buyers = new ArrayList<>();

    public CouponDto toDto() {
        return CouponDto.builder()
                .id(this.id)
                .companyId(this.companyId)
                .category(this.category)
                .title(this.title)
                .description(this.description)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .amount(this.amount)
                .price(this.price)
                .image(this.image)
                .buyers(this.buyers
                        .stream()
                        .map(CustomerEntity::toDto)
                        .collect(Collectors.toList()))
                .build();
    }
}
