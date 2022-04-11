package com.JB.couponsproject.services;


import com.JB.couponsproject.entities.CouponEntity;
import com.JB.couponsproject.entities.CustomerEntity;
import com.JB.couponsproject.enums.Category;
import com.JB.couponsproject.exceptions.ApplicationException;
import com.JB.couponsproject.exceptions.WrongCertificationsException;
import com.JB.couponsproject.repositories.CouponRepository;
import com.JB.couponsproject.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    //Dependencies
    private final CouponRepository couponRepository;
    private final CustomerRepository customerRepository;
    //State
    //TODO: Make the login functionality stateless
    Long customerId;

    //Methods: Login
    public void login(final String email, final String password) throws ApplicationException {
        final List<CustomerEntity> allCustomers = customerRepository.findAll();
        for (CustomerEntity customer :
                allCustomers) {
            if (customer.getEmail().equalsIgnoreCase(email) & customer.getPassword() == password.hashCode()) {
                customerId = customer.getId();
                return;
            }
        }
        throw new WrongCertificationsException("Wrong email or password");
    }

    //Methods: purchaseCoupon
    /*public void purchaseCoupon(Long couponId) throws ApplicationException {
        CouponEntity item = couponRepository.getById(couponId);
        //Validation 1: check that current amount isn't 0
        if (item.getAmount() == 0){
            throw new ApplicationException("Coupon is out of stock");
        }
        //Validation 2: check that the coupon wasn't already purchased by the customer
        final List<CouponEntity> coupons = getCustomerCoupons();
        for (CouponEntity coupon :
                coupons)  {
            if (coupon.getId()==couponId){
                throw new ApplicationException("Coupon already in customer's coupons list");
            }
        }
        //add to coupon_vs_customer

        //lower amount by current amount minus 1
        item.setAmount(item.getAmount()-1);

    }*/

    //Methods: purchaseCoupon 2
    /*public void purchaseCoupon(final Long couponId) throws ApplicationException {
        final Optional<CouponEntity> coupon = couponRepository.findById(couponId);
        if (coupon.isEmpty()){
            throw new ApplicationException("Coupon doesn't exist in the system.");
        }

        if ()

    }*/

    //Methods: get Customer's Coupons - all
    //customer id taken from state
    public List<CouponEntity> getCustomerCoupons() {
        final List<CouponEntity> coupons = couponRepository.getCustomerCoupons(customerId);
        return coupons;
    }

    //Methods: get Customer's Coupons - from category id
    //customer id taken from state
    public List<CouponEntity> getCustomerCoupons(final Category category) {
        final List<CouponEntity> coupons = couponRepository.getCustomerCouponsByCategory(customerId, category);
        return coupons;
    }

    //Methods: get Customer's Coupons - up to price x
    //customer id taken from state
    public List<CouponEntity> getCustomerCoupons(final double price) {
        final List<CouponEntity> coupons = couponRepository.findCustomerCouponsByPriceLessThan(customerId, price);
        return coupons;
    }

    //Methods: get Customer Details



}
