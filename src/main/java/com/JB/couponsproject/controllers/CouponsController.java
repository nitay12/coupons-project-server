package com.JB.couponsproject.controllers;

import com.JB.couponsproject.dto.CouponDto;
import com.JB.couponsproject.entities.CouponEntity;
import com.JB.couponsproject.enums.Category;
import com.JB.couponsproject.repositories.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("coupons")
@CrossOrigin
public class CouponsController {

    private final CouponRepository couponRepository;

    @GetMapping("all")
    public List<CouponEntity> getAllCoupons(){
        return couponRepository.findAll();
    }

    @GetMapping("single/{id}")
    public CouponDto getSingleCoupon(@PathVariable("id") final long id){
        return couponRepository.findById(id).get().toDto();
    }

    @GetMapping("category/{category}")
    public List<CouponEntity> getCouponByCategory(@PathVariable("category") final Category category){
        return couponRepository.getByCategory(category);
    }

    @GetMapping("maxprice/{maxPrice}")
    public List<CouponEntity> getCouponByMaxPrice(@PathVariable("maxPrice") final double maxPrice){
        return couponRepository.findByPriceLessThan(maxPrice);
    }

    @GetMapping("company/{company}")
    public List<CouponEntity> getCouponsByCompanyId(@PathVariable("company") final long companyId){
        return couponRepository.getByCompanyId(companyId);
    }

}
