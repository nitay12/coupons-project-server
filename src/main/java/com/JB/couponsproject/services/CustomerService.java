package com.JB.couponsproject.services;

import com.JB.couponsproject.entities.CouponEntity;
import com.JB.couponsproject.entities.CustomerEntity;
import com.JB.couponsproject.enums.Category;
import com.JB.couponsproject.enums.UserType;
import com.JB.couponsproject.exceptions.ApplicationException;
import com.JB.couponsproject.exceptions.WrongCredentialsException;
import com.JB.couponsproject.repositories.CouponRepository;
import com.JB.couponsproject.repositories.CustomerRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService implements ClientService {
    //Dependencies
    private final CouponRepository couponRepository;
    private final CustomerRepository customerRepository;
    //State
    @Getter
    private static Long customerId;
    private static String customerEmail;
    private final static UserType userType = UserType.CUSTOMER;

    //Methods: Login
    public boolean login(final String email, final String password) throws ApplicationException {
        final List<CustomerEntity> allCustomers = customerRepository.findAll();
        for (CustomerEntity customer :
                allCustomers) {
            if (customer.getEmail().equalsIgnoreCase(email) & Objects.equals(customer.getPassword(), String.valueOf(password.hashCode()))) {
                customerId = customer.getId();
                customerEmail = customer.getEmail();
                return true;
            }
        }
        throw new WrongCredentialsException("Wrong email or password");
    }

    @Override
    public UserType getUserType() {
        return userType;
    }

    @Override
    public String getEmail() {
        return customerEmail;
    }

    @Override
    public long getId() {
        return customerId;
    }

    //Methods: purchaseCoupon
    //verify values: coupon id exist, user id exist, user coupon relation wasn't established yet
    public void purchaseCoupon(final Long couponId, final Long customerId) throws ApplicationException {
        final Optional<CouponEntity> tmpCoupon = couponRepository.findById(couponId);
        if (tmpCoupon.isEmpty()) {
            throw new ApplicationException("Coupon doesn't exist in the system.");
        }
        final Optional<CustomerEntity> tmpCustomer = customerRepository.findById(customerId);
        if (tmpCustomer.isEmpty()) {
            throw new ApplicationException("Customer ID isn't valid");
        }
        if (tmpCustomer.get().getCoupons().contains(tmpCoupon.get())) {
            throw new ApplicationException("Coupon already in customer's coupons list");
        }
        CouponEntity coupon = tmpCoupon.get();
        if (coupon.getAmount() <= 0) {
            throw new ApplicationException("Coupon out of stock");
        }
        CustomerEntity customer = tmpCustomer.get();
        coupon.setAmount(coupon.getAmount() - 1);
        customer.purchaseCoupon(coupon);
        customerRepository.save(customer);
    }

    //Methods: get Customer's Coupons - all
    //customer id taken from state
    public List<CouponEntity> getCustomerCoupons(final long customerId) {
//        final List<CouponEntity> coupons = couponRepository.getCustomerCoupons(customerId);
//        return coupons;
        return customerRepository.findById(customerId).get().getCoupons();
    }

    //Methods: get Customer's Coupons - from category id
    public List<CouponEntity> getCustomerCouponsByCategory(final Category category, final long customerId) {
        return couponRepository.getCustomerCoupons(customerId).stream().filter(x -> x.getCategory().equals(category)).toList();
    }

    //Methods: get Customer's Coupons - up to price x
    //customer id taken from state
    public List<CouponEntity> getCustomerCoupons(final double price, final long customerId) {
        return couponRepository.getCustomerCoupons(customerId).stream().filter(x -> x.getPrice() <= price).toList();
    }

    //TODO: Check if customer is logged in with LoginManager
    //TODO: Check if customer exists
    public CustomerEntity getLoggedInCustomer(final long customerId) {
        return customerRepository.findById(customerId).get();
    }
}
