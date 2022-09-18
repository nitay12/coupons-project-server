package com.JB.couponsproject.services;

import com.JB.couponsproject.dto.CouponDto;
import com.JB.couponsproject.entities.CompanyEntity;
import com.JB.couponsproject.entities.CouponEntity;
import com.JB.couponsproject.entities.CustomerEntity;
import com.JB.couponsproject.enums.Category;
import com.JB.couponsproject.enums.EntityType;
import com.JB.couponsproject.enums.UserType;
import com.JB.couponsproject.exceptions.*;
import com.JB.couponsproject.repositories.CompanyRepository;
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
public class CompanyService implements ClientService {
    //Dependencies
    private final CompanyRepository companyRepository;
    private final CouponRepository couponRepository;
    private final CustomerRepository customerRepository;
    @Getter
    private static Long companyId;
    private static String companyEmail;
    private static UserType userType = UserType.COMPANY;



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
        throw new WrongCertificationsException("Wrong email or password");
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

    public long findIdByEmail(String email){
                return companyRepository.findByEmail(email).get(0).getId();

    }
    public CouponEntity addCoupon(CouponDto couponDto) throws ApplicationException {
        //Same title
        if (couponRepository.existsByTitleAndCompanyId(couponDto.getTitle(),companyId)) {
            throw new TitleExistException("This title is already exist");
        }
        return couponRepository.save(couponDto.toEntity());
    }

    public long updateCoupon(CouponDto couponDto) throws ApplicationException {
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
        final CouponEntity couponEntity = couponDto.toEntity();
        couponRepository.save(couponEntity);
        return couponEntity.getId();
    }

    public void deleteCoupon(Long id) throws EntityNotFoundException, DeleteException {
        if (!couponRepository.existsById(id)) {
            throw new EntityNotFoundException(EntityType.COUPON, id);
        }
        if (!couponRepository.existsByIdAndCompanyId(id, companyId)){
            throw new DeleteException("Only coupon of the logged in company can be deleted");
        }
        final CouponEntity coupon = couponRepository.findById(id).get();
        final List<CustomerEntity> buyers = coupon.getBuyers();
        for (CustomerEntity customer :
                buyers) {
            customer.deleteCoupon(coupon);
            customerRepository.save(customer);
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
            return coupon.get().toDto();
        }
        else {
            throw new EntityNotFoundException("Coupon not found");
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
