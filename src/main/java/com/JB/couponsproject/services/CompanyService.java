package com.JB.couponsproject.services;

import com.JB.couponsproject.dto.CouponDto;
import com.JB.couponsproject.entities.CompanyEntity;
import com.JB.couponsproject.entities.CouponEntity;
import com.JB.couponsproject.enums.Category;
import com.JB.couponsproject.enums.EntityType;
import com.JB.couponsproject.exceptions.*;
import com.JB.couponsproject.repositories.CompanyRepository;
import com.JB.couponsproject.repositories.CouponRepository;
import com.JB.couponsproject.util.ObjectMappingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CompanyService {
    //Dependencies
    private final CompanyRepository companyRepository;
    private final CouponRepository couponRepository;
    //State
    //TODO: Make the login functionality stateless
    private Long companyId;

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
        if (
                couponRepository.existsByTitleAndCompanyId(couponDto.getTitle(),companyId)
//                isTitleExistByCompanyId(companyId, couponDto)
        ) {
            throw new TitleExistException("This title is already exist");
        }
        couponDto.setCompanyId(companyId);
        final CouponEntity newCoupon = couponRepository.save(ObjectMappingUtil.couponDtoToEntity(couponDto));
        return newCoupon.getId();
    }

    public long updateCoupon(CouponDto couponDto) throws ApplicationException {
        //Verifications
        //Coupon already exist
        if (!couponRepository.existsById(couponDto.getId())) {
            throw new EntityNotFoundException(EntityType.coupon, couponDto.getId());
        }
        //Title already exist (from logged in company coupons)
        if (
            isTitleExistByCompanyId(companyId, couponDto)
        ) {
            throw new TitleExistException("This title is already exist");
        }
        //Company Id updated
        if (!couponRepository.existsByIdAndCompanyId(couponDto.getId(), companyId)) {
            throw new UpdateException("The company id cannot be updated");
        }
        final CouponEntity couponEntity = ObjectMappingUtil.couponDtoToEntity(couponDto);
        couponRepository.save(couponEntity);
        return couponEntity.getId();
    }

    public void deleteCoupon(Long id) throws EntityNotFoundException, DeleteException {
        if (!couponRepository.existsById(id)) {
            throw new EntityNotFoundException(EntityType.coupon, id);
        }
        if (!couponRepository.existsByIdAndCompanyId(id, companyId)){
            throw new DeleteException("Only coupon of the logged in company can be deleted");
        }
        couponRepository.deleteById(id);
    }

    public List<CouponEntity> getCompanyCoupons() {
        return couponRepository.getByCompanyId(companyId);
    }

    public List<CouponEntity> getCompanyCoupons(Category category) {
        return couponRepository.getByCompanyIdAndCategory(companyId, category);
    }

    public List<CouponEntity> getCompanyCoupons(double maxPrice) {
        return couponRepository.findByCompanyIdAndPriceLessThan(companyId, maxPrice);
    }
    public CompanyEntity getLoggedInCompany() throws ApplicationException {
        if (Objects.nonNull(companyId)) {
            return companyRepository.findById(companyId).get();
        }
        else{
            throw new ApplicationException("No company logged in");
        }
    }

    //TODO: check if can be done by JPA init methods
    private boolean isTitleExistByCompanyId(long companyId, CouponDto couponDto) {
        final List<CouponEntity> companyCouponsById = couponRepository.getByCompanyId(companyId);
        for (CouponEntity companyCoupon :
                companyCouponsById) {
            if (companyCoupon.getTitle().equalsIgnoreCase(couponDto.getTitle())) {
                if(companyCoupon.getId().equals(couponDto.getId())){
                    continue;
                }
                return true;
            }
        }
        return false;
    }
}
