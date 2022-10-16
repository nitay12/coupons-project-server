package com.JB.couponsproject.controllers;

import com.JB.couponsproject.entities.CouponEntity;
import com.JB.couponsproject.enums.Category;
import com.JB.couponsproject.exceptions.ApplicationException;
import com.JB.couponsproject.security.JwtUtil;
import com.JB.couponsproject.security.JwtWrapper;
import com.JB.couponsproject.services.AdminService;
import com.JB.couponsproject.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("customer")
public class CustomerController {
    private final CustomerService customerService;
    //required for profile manipulation and delete account button.
    private final AdminService adminService;

    private long setCustomerIdFromToken(JwtWrapper jwtHeader) {
        final String token = jwtHeader.getToken();
        return JwtUtil.extractId(token);
    }
    //PurchaseCoupon - POST
    @PutMapping("purchase/{couponId}")
    public void purchaseCoupon(@PathVariable("couponId") long couponId,@RequestHeader("Authorization") JwtWrapper jwtHeader) throws ApplicationException {
        customerService.purchaseCoupon(couponId, setCustomerIdFromToken(jwtHeader));
    }
    //GetCustomerCoupons - by customerId long - GET
    @GetMapping("coupons")
    public List<CouponEntity> getCoupons(@RequestHeader("Authorization") JwtWrapper jwtHeader) {
        return customerService.getCustomerCoupons(setCustomerIdFromToken(jwtHeader));
    }
    //GetCustomerCoupons - by customerId long && Category - GET
    @GetMapping("coupons/category/{category}")
    public List<CouponEntity> getCouponByCategory(@RequestHeader("Authorization") JwtWrapper jwtHeader, @PathVariable("category") final Category category){
        return customerService.getCustomerCouponsByCategory(category, setCustomerIdFromToken(jwtHeader));
    }
    //GetCustomerCoupons - by customerId long && maxPrice double - GET
    @GetMapping("coupons/maxPrice/{maxPrice}")
    public List<CouponEntity> getCouponByMaxPrice(@RequestHeader("Authorization") JwtWrapper jwtHeader, @PathVariable("maxPrice") final double maxPrice){
        return customerService.getCustomerCoupons(maxPrice, setCustomerIdFromToken(jwtHeader));
    }
    //Personal Bonus - Delete my account option - DELETE - add protection with userId to confirm
    @DeleteMapping("deleteCustomer/{password}")
    public void deleteCustomer(@RequestHeader("Authorization") JwtWrapper jwtHeader, String password) throws ApplicationException {
        if (adminService.getCustomerById(setCustomerIdFromToken(jwtHeader)).getPassword().equals(password)){
            adminService.deleteCustomer(setCustomerIdFromToken(jwtHeader));
        }
    }

    //Update profile - PUT


}
