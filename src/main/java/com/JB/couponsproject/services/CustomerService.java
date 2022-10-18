package com.JB.couponsproject.services;

import com.JB.couponsproject.dto.CustomerDto;
import com.JB.couponsproject.entities.CouponEntity;
import com.JB.couponsproject.entities.CustomerEntity;
import com.JB.couponsproject.enums.Category;
import com.JB.couponsproject.enums.EntityType;
import com.JB.couponsproject.enums.UserType;
import com.JB.couponsproject.exceptions.ApplicationException;
import com.JB.couponsproject.exceptions.EntityNotFoundException;
import com.JB.couponsproject.exceptions.WrongCredentialsException;
import com.JB.couponsproject.repositories.CouponRepository;
import com.JB.couponsproject.repositories.CustomerRepository;
import com.JB.couponsproject.tests.CompanyServiceTest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(CompanyServiceTest.class);
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
    public void purchaseCoupon(final Long couponId, final Long customerId) {
        final Optional<CouponEntity> tmpCoupon = couponRepository.findById(couponId);
        if (tmpCoupon.isEmpty()) {
            logger.info("Coupon "+couponId+" doesn't exist in the system.");
            return;
        }
        final Optional<CustomerEntity> tmpCustomer = customerRepository.findById(customerId);
        if (tmpCustomer.isEmpty()) {
            logger.info("Customer ID isn't valid");
            return;
        }
        if (tmpCustomer.get().getCoupons().contains(tmpCoupon.get())) {
            logger.info("Coupon already in customer's coupons list");
            return;
        }
        CouponEntity coupon = tmpCoupon.get();
        if (coupon.getAmount() <= 0) {
            logger.info("Coupon out of stock");
            return;
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

    public Long updateCustomer(CustomerDto customerDto, long id) throws ApplicationException {
        if (!customerRepository.existsById(customerDto.getId())){
            throw new EntityNotFoundException(EntityType.CUSTOMER, customerDto.getId());
        }
        if (customerDto.getId()!=id){
            throw new ApplicationException("You are not the one!");
        }
        final CustomerEntity customer = customerDto.toEntity();
        customerRepository.saveAndFlush(customer);
        return customer.getId();
    }

    public CustomerDto getCustomerDetails(Long customerId) {
        return customerRepository.findById(customerId).get().toDto();
    }
}
