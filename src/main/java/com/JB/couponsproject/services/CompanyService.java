package com.JB.couponsproject.services;

import com.JB.couponsproject.dto.CouponDto;
import com.JB.couponsproject.entities.CompanyEntity;
import com.JB.couponsproject.entities.CouponEntity;
import com.JB.couponsproject.exceptions.ApplicationException;
import com.JB.couponsproject.exceptions.TitleExistException;
import com.JB.couponsproject.exceptions.WrongCertificationsException;
import com.JB.couponsproject.repositories.CompanyRepository;
import com.JB.couponsproject.repositories.CouponRepository;
import com.JB.couponsproject.repositories.CustomerRepository;
import com.JB.couponsproject.util.ObjectMappingUtil;
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

    public long addCoupon(CouponDto couponDto) throws ApplicationException {
        //Verifications
        //Same title
        if(isTitleExistByCompanyId(companyId,couponDto)){
            throw new TitleExistException("This title is already exist");
        }
        couponDto.setCompanyId(companyId);
        final CouponEntity newCoupon = couponRepository.save(ObjectMappingUtil.couponDtoToEntity(couponDto));
        return newCoupon.getId();
    }
    public long update(CouponDto couponDto) throws TitleExistException {
        if(isTitleExistByCompanyId(companyId,couponDto)){
            throw new TitleExistException("This title is already exist");
        }
        couponDto.setCompanyId(companyId);
        final CouponEntity newCoupon = ObjectMappingUtil.couponDtoToEntity(couponDto);
        couponRepository.updateCoupon(newCoupon.getCategory(),
                newCoupon.getTitle(),
                newCoupon.getDescription(),
                newCoupon.getStartDate(),
                newCoupon.getEndDate(),
                newCoupon.getAmount(),
                newCoupon.getPrice(),
                newCoupon.getImage());
        
        return newCoupon.getId();
    }
    
    public boolean isTitleExistByCompanyId(long companyId,CouponDto couponDto){
        final List<CouponEntity> companyCouponsById = couponRepository.getByCompanyId(companyId);
        for (CouponEntity companyCoupon :
                companyCouponsById) {
            if (companyCoupon.getTitle().equalsIgnoreCase(couponDto.getTitle())) {
                return true;
            }
        }
            return false;
    }
}
