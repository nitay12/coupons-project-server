package com.JB.couponsproject.services;

import com.JB.couponsproject.entities.CompanyEntity;
import com.JB.couponsproject.entities.CouponEntity;
import com.JB.couponsproject.exceptions.ApplicationException;
import com.JB.couponsproject.exceptions.TitleExistException;
import com.JB.couponsproject.exceptions.WrongCertificationsException;
import com.JB.couponsproject.repositories.CompanyRepository;
import com.JB.couponsproject.repositories.CouponRepository;
import com.JB.couponsproject.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {
    //Dependencies
    private final CompanyRepository companyRepository;
    private final CouponRepository couponRepository;
    private final CustomerRepository customerRepository;
    //State
    //TODO: Make the login functionality stateless
    Long companyId;

    //Methods
    public void login(String email, String password) throws ApplicationException {
        final List<CompanyEntity> allCompanies = companyRepository.findAll();
        for (CompanyEntity company :
                allCompanies) {
            if (company.getEmail().equalsIgnoreCase(email) & company.getPassword() == password.hashCode()) {
                companyId = company.getId();
                return;
            }
        }
            throw new WrongCertificationsException("Wrong email or password");
    }

    public Long addCoupon(CouponEntity coupon) throws ApplicationException {
        //Verifications
        //Same title
        final List<CouponEntity> companyCoupons = couponRepository.getCompanyCoupons(companyId);
        for (CouponEntity companyCoupon :
                companyCoupons) {
            if (companyCoupon.getTitle().equalsIgnoreCase(coupon.getTitle())) {
                throw new TitleExistException("This title is already exist");
            }
        }
        coupon.setCompanyId(companyId);
        final CouponEntity newCoupon = couponRepository.save(coupon);
        return newCoupon.getId();
    }


}
