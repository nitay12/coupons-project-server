package com.JB.couponsproject.controllers;

import com.JB.couponsproject.dto.CouponDto;
import com.JB.couponsproject.entities.CouponEntity;
import com.JB.couponsproject.enums.Category;
import com.JB.couponsproject.exceptions.ApplicationException;
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
@CrossOrigin()
public class CompanyController {
    private final CompanyService companyService;

    private void setCompanyIdFromToken(CouponDto couponDto, JwtWrapper jwtHeader) {
        final Long companyId = getCompanyId(jwtHeader);
        couponDto.setCompanyId(companyId);
    }

    private Long getCompanyId(JwtWrapper jwtHeader) {
        final String token = jwtHeader.getToken();
        return JwtUtil.extractId(token);
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
    @DeleteMapping("delete/{id}")
    public void deleteCoupon(@PathVariable("id") long id, @RequestHeader("Authorization") JwtWrapper jwtHeader) throws EntityNotFoundException {
        final String token = jwtHeader.getToken();
        final Long companyId = JwtUtil.extractId(token);
        companyService.deleteCompanyCoupon(id, companyId);
    }

    //getCompanyCoupons - companyId - get
    @GetMapping("coupons")
    public List<CouponEntity> getCoupons(@RequestHeader("Authorization") JwtWrapper jwtHeader) {
        Long id = getCompanyId(jwtHeader);
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
