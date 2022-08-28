package com.JB.couponsproject.controllers;

import com.JB.couponsproject.entities.CouponEntity;
import com.JB.couponsproject.enums.Category;
import com.JB.couponsproject.exceptions.ApplicationException;
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

    //PurchaseCoupon - POST
    @PutMapping("purchase/{couponId}/{customerId}")
    public void purchaseCoupon(@PathVariable("couponId") long couponId,@PathVariable("customerId") long customerId) throws ApplicationException {
        customerService.purchaseCoupon(couponId, customerId);
    }
    //GetCustomerCoupons - by customerId long - GET
    @GetMapping("coupons/{id}")
    public List<CouponEntity> getCoupons(@PathVariable("id") final long id) {
        return customerService.getCustomerCoupons(id);
    }
    //GetCustomerCoupons - by customerId long && Category - GET
    @GetMapping("coupon/{id}/{category}")
    public List<CouponEntity> getCoupon(@PathVariable("id") final long id, @PathVariable("category") final Category category){
        return customerService.getCustomerCoupons(category, id);
    }
    //GetCustomerCoupons - by customerId long && maxPrice double - GET
    @GetMapping("coupon/{id}/{maxPrice}")
    public List<CouponEntity> getCoupon(@PathVariable("id") final long id, @PathVariable("maxPrice") final double maxPrice){
        return customerService.getCustomerCoupons(maxPrice, id);
    }
    //Personal Bonus - Delete my account option - DELETE - add protection with userId to confirm
    @DeleteMapping("DeleteCustomer/{id}/{password}")
    public void deleteCustomer(long id, String password) throws ApplicationException {
        if (adminService.getCustomerById(id).getPassword().equals(password)){
            adminService.deleteCustomer(id);
        }
    }

    //Update profile - PUT


}
