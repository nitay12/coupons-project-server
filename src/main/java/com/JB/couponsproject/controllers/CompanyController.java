package com.JB.couponsproject.controllers;

import com.JB.couponsproject.dto.CouponDto;
import com.JB.couponsproject.entities.CouponEntity;
import com.JB.couponsproject.enums.Category;
import com.JB.couponsproject.exceptions.ApplicationException;
import com.JB.couponsproject.exceptions.DeleteException;
import com.JB.couponsproject.exceptions.EntityNotFoundException;
import com.JB.couponsproject.services.AdminService;
import com.JB.couponsproject.services.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("company")
public class CompanyController {
    private final CompanyService companyService;
    //required for profile manipulation and delete company button.
    private final AdminService adminService;

    //addCoupon - CouponDto couponDto,long companyId - post
    @PostMapping("addCoupon/{companyId}")
    public void addCoupon(@RequestBody CouponDto couponDto, @PathVariable("companyId") long companyId) throws ApplicationException {
        companyService.addCoupon(couponDto,companyId);
    }
    //updateCoupon - CouponDto couponDto , long companyId - put
    @PutMapping("update/coupon/{companyId}")
    public void updateCoupon(@RequestBody CouponDto couponDto, @PathVariable("companyId") long companyId) throws ApplicationException {
        companyService.updateCoupon(couponDto,companyId);
    }
    //deleteCoupon - Long id,long companyId - delete
    @DeleteMapping("delete/{id}/{companyId}")
    public void deleteCoupon(@PathVariable("id") long id, @PathVariable("companyId") long companyId) throws DeleteException, EntityNotFoundException {
        companyService.deleteCoupon(id, companyId);
    }
    //getCompanyCoupons - companyId - get
    @GetMapping("coupon/{id}")
    public List<CouponEntity> getCoupon(@PathVariable("id") final long id){
        return companyService.getCompanyCoupons(id);
    }
    //getCompanyCoupons - category , companyId - get
    @GetMapping("coupon/{id}/{category}")
    public List<CouponEntity> getCoupon(@PathVariable("id") final long id, @PathVariable("category") final Category category){
        return companyService.getCompanyCoupons(category, id);
    }
    //getCompanyCoupons - maxPrice , companyId - get
    @GetMapping("coupon/{id}/{maxPrice}")
    public List<CouponEntity> getCoupon(@PathVariable("id") final long id, @PathVariable("maxPrice") final double maxPrice){
        return companyService.getCompanyCoupons(maxPrice, id);
    }
}
