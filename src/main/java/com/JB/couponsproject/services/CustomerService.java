package com.JB.couponsproject.services;


import com.JB.couponsproject.dto.CustomerDto;
import com.JB.couponsproject.entities.CompanyEntity;
import com.JB.couponsproject.entities.CouponEntity;
import com.JB.couponsproject.entities.CustomerEntity;
import com.JB.couponsproject.enums.Category;
import com.JB.couponsproject.exceptions.ApplicationException;
import com.JB.couponsproject.exceptions.WrongCertificationsException;
import com.JB.couponsproject.repositories.CouponRepository;
import com.JB.couponsproject.repositories.CustomerRepository;
import com.JB.couponsproject.util.ObjectMappingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService implements ClientService {
    //Dependencies
    private final CouponRepository couponRepository;
    private final CustomerRepository customerRepository;
    //State

    //Methods: Login
    public boolean login(final String email, final String password) throws ApplicationException {
        final List<CustomerEntity> allCustomers = customerRepository.findAll();
        for (CustomerEntity customer :
                allCustomers) {
            if (customer.getEmail().equalsIgnoreCase(email) & customer.getPassword() == password.hashCode()) {
                return true;
            }
        }
        throw new WrongCertificationsException("Wrong email or password");
    }

    //Methods: purchaseCoupon
    //verify values: coupon id exist, user id exist, user coupon relation wasn't established yet
    public void purchaseCoupon(final Long couponId, final Long customerId) throws ApplicationException {
        final Optional<CouponEntity> tmpCoupon = couponRepository.findById(couponId);
        if (tmpCoupon.isEmpty()){
            throw new ApplicationException("Coupon doesn't exist in the system.");
        }
        final Optional<CustomerEntity> tmpCustomer = customerRepository.findById(customerId);
        if (tmpCustomer.isEmpty()){
            throw new ApplicationException("Customer ID isn't valid");
        }
        if (tmpCustomer.get().getCoupons().contains(tmpCoupon.get())){
            throw new ApplicationException("Coupon already in customer's coupons list");
        }
        CouponEntity coupon = tmpCoupon.get();
        if (coupon.getAmount()<=0){
            throw new ApplicationException("Coupon out of stock");
        }
        CustomerEntity customer = tmpCustomer.get();
        customer.getCoupons().add(coupon);
        coupon.setAmount(coupon.getAmount()-1);
        //customer.purchaseCoupon(coupon);
        ObjectMappingUtil.customerEntityToDto(customerRepository.save(customer));

    }

    //Methods: get Customer's Coupons - all
    //customer id taken from state
    public List<CouponEntity> getCustomerCoupons(long customerId) {
        final List<CouponEntity> coupons = couponRepository.getCustomerCoupons(customerId);
        return coupons;
    }

    public List<CouponEntity> getCustomerCoupons(final Long customerId) {
        final List<CouponEntity> coupons = couponRepository.getCustomerCoupons(customerId);
        return coupons;
    }

    //Methods: get Customer's Coupons - from category id
    public List<CouponEntity> getCustomerCoupons(final Category category,long customerId) {
        final List<CouponEntity> coupons = couponRepository.getCustomerCouponsByCategory(customerId, category);
        return coupons;
    }
// seems like duplicate function
//    public List<CouponEntity> getCustomerCoupons(final Category category, final Long customerId) {
//        final List<CouponEntity> coupons = couponRepository.getCustomerCouponsByCategory(customerId, category);
//        return coupons;
//    }

    //Methods: get Customer's Coupons - up to price x
    //customer id taken from state
    public List<CouponEntity> getCustomerCoupons(final double price, long customerId) {
        final List<CouponEntity> coupons = couponRepository.findCustomerCouponsByPriceLessThan(customerId, price);
        return coupons;
    }

    public List<CouponEntity> getCustomerCoupons(final double price, final Long customerId) {
        final List<CouponEntity> coupons = couponRepository.findCustomerCouponsByPriceLessThan(customerId, price);
        return coupons;
    }

    public CustomerEntity getLoggedInCustomer(long customerId) throws ApplicationException {
        if (Objects.nonNull(customerId)) {
            return customerRepository.findById(customerId).get();
        }
        else{
            throw new ApplicationException("No customer logged in");
        }
    }
}
