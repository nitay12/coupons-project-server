package com.JB.couponsproject.services;

import com.JB.couponsproject.dto.CompanyDto;
import com.JB.couponsproject.dto.CouponDto;
import com.JB.couponsproject.entities.CompanyEntity;
import com.JB.couponsproject.entities.CouponEntity;
import com.JB.couponsproject.enums.Category;
import com.JB.couponsproject.enums.EntityType;
import com.JB.couponsproject.enums.UserType;
import com.JB.couponsproject.exceptions.*;
import com.JB.couponsproject.repositories.CompanyRepository;
import com.JB.couponsproject.repositories.CouponRepository;
import com.JB.couponsproject.repositories.CustomerRepository;
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
public class CompanyService implements ClientService {
    //Dependencies
    private final CompanyRepository companyRepository;
    private final CouponRepository couponRepository;
    @Getter
    private static Long companyId;
    private static String companyEmail;
    private static final UserType userType = UserType.COMPANY;
    Logger LOGGER = LoggerFactory.getLogger(AdminService.class);

    //Methods
    public boolean login(String email, String password) throws ApplicationException {
        if (!companyRepository.existsByEmail(email)) {
            throw new EntityNotFoundException("This email is not exist in the DB");
        }
        final List<CompanyEntity> allCompanies = companyRepository.findAll();
        for (CompanyEntity company :
                allCompanies) {
            if (company.getEmail().equalsIgnoreCase(email) & Objects.equals(company.getPassword(), String.valueOf(password.hashCode()))) {
                companyId = company.getId();
                companyEmail = company.getEmail();
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
        return companyEmail;
    }

    @Override
    public long getId() {
        return companyId;
    }

    public CouponEntity addCoupon(CouponDto couponDto) throws ApplicationException {
        //Same title
        if (couponRepository.existsByTitleAndCompanyId(couponDto.getTitle(), companyId)) {
            throw new TitleExistException("This title is already exist");
        }
        return couponRepository.save(couponDto.toEntity());
    }

    public long updateCoupon(CouponDto couponDto) throws ApplicationException {
        //Coupon id already exist
        if (!couponRepository.existsById(couponDto.getId())) {
            throw new EntityNotFoundException(EntityType.COUPON, couponDto.getId());
        }
        //Title already exist (from logged in company coupons)
        if (
                isTitleExistByCompanyId(companyId, couponDto)
        ) {
            throw new TitleExistException("This title is already exist");
        }
        //Company ID not match
        if (!couponRepository.existsByIdAndCompanyId(couponDto.getId(), companyId)) {
            throw new UpdateException("Wrong company ID");
        }
        final CouponEntity couponEntity = couponDto.toEntity();
        couponRepository.saveAndFlush(couponEntity);
        return couponEntity.getId();
    }

    public void deleteCoupon(Long id) throws EntityNotFoundException {
        if (!couponRepository.existsById(id)) {
            throw new EntityNotFoundException(EntityType.COUPON, id);
        }
        LOGGER.info("Deleting coupon: " + id);
        couponRepository.deleteById(id);
    }

    public void deleteCompanyCoupon(Long id, Long companyId) throws EntityNotFoundException, ForbiddenException {
        if (!couponRepository.existsById(id)) {
            throw new EntityNotFoundException(EntityType.COUPON, id);
        }
        if (!(couponRepository.findById(id).get().getCompanyId() == companyId)) {
            throw new ForbiddenException("This coupon is not belong to the logged-in company");
        }
        couponRepository.deleteById(id);
    }


    public List<CouponEntity> getCompanyCoupons(long companyId) {
        return couponRepository.getByCompanyId(companyId);
    }

    public List<CouponEntity> getCompanyCoupons(Category category, long companyId) {
        return couponRepository.getByCompanyIdAndCategory(companyId, category);
    }

    public List<CouponEntity> getCompanyCoupons(double maxPrice, long companyId) {
        return couponRepository.findByCompanyIdAndPriceLessThan(companyId, maxPrice);
    }

    public CouponDto getOneCoupon(Long companyId, Long couponId) throws EntityNotFoundException {
        final List<CouponEntity> companyCoupons = getCompanyCoupons(companyId);
        final Optional<CouponEntity> coupon = companyCoupons.stream().filter(c -> c.getId().equals(companyId)).findAny();
        if (coupon.isPresent()) {
            return coupon.get().toDto();
        } else {
            throw new EntityNotFoundException("Coupon not found");
        }
    }

    private boolean isTitleExistByCompanyId(long companyId, CouponDto couponDto) {
        final List<CouponEntity> companyCouponsById = couponRepository.getByCompanyId(companyId);
        for (CouponEntity companyCoupon :
                companyCouponsById) {
            if (companyCoupon.getTitle().equalsIgnoreCase(couponDto.getTitle())) {
                if (companyCoupon.getId().equals(couponDto.getId())) {
                    continue;
                }
                return true;
            }
        }
        return false;
    }

    public Long updateCompany(CompanyDto companyDto, Long id) throws ApplicationException {
        if (companyRepository.existsById(companyDto.getId())) {
            throw new EntityNotFoundException(EntityType.COMPANY, companyDto.getId());
        }
        if (companyDto.getId() != id) {
            throw new ApplicationException("You are not the one");
        }
        final CompanyEntity companyEntity = companyDto.toEntity();
        companyRepository.saveAndFlush(companyEntity);
        return companyEntity.getId();
    }

    public CompanyDto getCompanyDetails(long companyId){
        return companyRepository.findById(companyId).get().toDto();
    }
}
