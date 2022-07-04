package com.JB.couponsproject.services;

import com.JB.couponsproject.dto.CompanyDto;
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

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyService implements ClientService {
    //Dependencies
    private final CompanyRepository companyRepository;
    private final CouponRepository couponRepository;

    //Methods
    public boolean login(String email, String password) throws ApplicationException {
        if (!companyRepository.existsByEmail(email)) {
            throw new EntityNotFoundException("This email is not exist in the DB");
        }
        final List<CompanyEntity> allCompanies = companyRepository.findAll();
        for (CompanyEntity company :
                allCompanies) {
            if (company.getEmail().equalsIgnoreCase(email) & Objects.equals(company.getPassword(), String.valueOf(password.hashCode()))) {
                return true;
            }
        }
        throw new WrongCertificationsException("Wrong email or password");
    }

    public long findIdByEmail(String email){
                return companyRepository.findByEmail(email).get(0).getId();

    }
    public long addCoupon(CouponDto couponDto,long companyId) throws ApplicationException {
        //Verifications
        //Same title
        if (
                couponRepository.existsByTitleAndCompanyId(couponDto.getTitle(),companyId)
        ) {
            throw new TitleExistException("This title is already exist");
        }
        couponDto.setCompanyId(companyId);
        final CouponEntity newCoupon = couponRepository.save(ObjectMappingUtil.couponDtoToEntity(couponDto));
        return newCoupon.getId();
    }

    public long updateCoupon(CouponDto couponDto, long companyId) throws ApplicationException {
        //Verifications
        //Coupon not exist
        if (!couponRepository.existsById(couponDto.getId())) {
            throw new EntityNotFoundException(EntityType.COUPON, couponDto.getId());
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

    public void deleteCoupon(Long id,long companyId) throws EntityNotFoundException, DeleteException {
        if (!couponRepository.existsById(id)) {
            throw new EntityNotFoundException(EntityType.COUPON, id);
        }
        if (!couponRepository.existsByIdAndCompanyId(id, companyId)){
            throw new DeleteException("Only coupon of the logged in company can be deleted");
        }
        couponRepository.deleteById(id);
    }

    public List<CouponEntity> getCompanyCoupons(long companyId) {
        return couponRepository.getByCompanyId(companyId);
    }

    public List<CouponEntity> getCompanyCoupons(Category category,long companyId) {
        return couponRepository.getByCompanyIdAndCategory(companyId, category);
    }

    public List<CouponEntity> getCompanyCoupons(double maxPrice,long companyId) {
        return couponRepository.findByCompanyIdAndPriceLessThan(companyId, maxPrice);
    }
    public CouponDto getOneCoupon(Long companyId, Long couponId) throws EntityNotFoundException {
        final List<CouponEntity> companyCoupons = getCompanyCoupons(companyId);
        final Optional<CouponEntity> coupon = companyCoupons.stream().filter(c -> c.getId().equals(companyId)).findAny();
        if(coupon.isPresent()){
            return ObjectMappingUtil.couponEntityToCouponDto(coupon.get());
        }
        else {
            throw new EntityNotFoundException("Coupon not found");
        }
    }

    // Consider Removing this function
//    public CompanyEntity getLoggedInCompany() throws ApplicationException {
//        if (Objects.isNull(companyId)) {
//            throw new ApplicationException("No company logged in");
//        }
//        final Optional<CompanyEntity> loggedInCompany = companyRepository.findById(companyId);
//        if(loggedInCompany.isEmpty()){
//            throw new ApplicationException("Cannot retrieve logged in company");
//        }
//        else{
//            return loggedInCompany.get();
//        }
//    }

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
