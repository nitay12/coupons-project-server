package com.JB.couponsproject.controllers;

import com.JB.couponsproject.dto.CouponDto;
import com.JB.couponsproject.entities.CouponEntity;
import com.JB.couponsproject.enums.Category;
import com.JB.couponsproject.exceptions.ApplicationException;
import com.JB.couponsproject.exceptions.DeleteException;
import com.JB.couponsproject.exceptions.EntityNotFoundException;
import com.JB.couponsproject.security.JwtUtil;
import com.JB.couponsproject.security.JwtWrapper;
import com.JB.couponsproject.services.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("company")
public class CompanyController {
    private final CompanyService companyService;

    private void setCompanyIdFromToken(CouponDto couponDto, JwtWrapper jwtHeader) {
        final String token = jwtHeader.getJwtToken();
        final Long companyId = JwtUtil.extractId(token);
        couponDto.setCompanyId(companyId);
    }

    //addCoupon - CouponDto couponDto - POST
    @PostMapping(value = "coupons", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public CouponEntity addCoupon(@RequestBody CouponDto couponDto, @RequestHeader("Authorization") JwtWrapper jwtHeader) throws ApplicationException {
        setCompanyIdFromToken(couponDto, jwtHeader);
        return companyService.addCoupon(couponDto);
    }


    //updateCoupon - CouponDto couponDto , long companyId - put
    @PutMapping("coupons")
    public Long updateCoupon(@RequestBody CouponDto couponDto, @RequestHeader("Authorization") JwtWrapper jwtHeader) throws ApplicationException {
        setCompanyIdFromToken(couponDto, jwtHeader);
        return companyService.updateCoupon(couponDto);
    }

    //deleteCoupon - Long id,long companyId - delete
    @DeleteMapping("delete/{id}/{companyId}")
    public void deleteCoupon(@PathVariable("id") long id, @PathVariable("companyId") long companyId) throws DeleteException, EntityNotFoundException {
        companyService.deleteCoupon(id);
    }

    //getCompanyCoupons - companyId - get
    @GetMapping("coupon/{id}")
    public List<CouponEntity> getCoupon(@PathVariable("id") final long id) {
        return companyService.getCompanyCoupons(id);
    }

    //getCompanyCoupons - category , companyId - get
    @GetMapping("coupon/{id}/{category}")
    public List<CouponEntity> getCoupon(@PathVariable("id") final long id, @PathVariable("category") final Category category) {
        return companyService.getCompanyCoupons(category, id);
    }

    //getCompanyCoupons - maxPrice , companyId - get
    @GetMapping("coupon/{id}/{maxPrice}")
    public List<CouponEntity> getCoupon(@PathVariable("id") final long id, @PathVariable("maxPrice") final double maxPrice) {
        return companyService.getCompanyCoupons(maxPrice, id);
    }
}
