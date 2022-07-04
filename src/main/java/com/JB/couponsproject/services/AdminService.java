package com.JB.couponsproject.services;

import com.JB.couponsproject.dto.CompanyDto;
import com.JB.couponsproject.dto.CustomerDto;
import com.JB.couponsproject.entities.CompanyEntity;
import com.JB.couponsproject.entities.CustomerEntity;
import com.JB.couponsproject.enums.EntityType;
import com.JB.couponsproject.exceptions.ApplicationException;
import com.JB.couponsproject.exceptions.EntityNotFoundException;
import com.JB.couponsproject.exceptions.WrongCertificationsException;
import com.JB.couponsproject.repositories.CompanyRepository;
import com.JB.couponsproject.repositories.CouponRepository;
import com.JB.couponsproject.repositories.CustomerRepository;
import com.JB.couponsproject.constants.TestData;
import com.JB.couponsproject.util.ObjectMappingUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Transactional
@Service
@RequiredArgsConstructor
public class AdminService implements ClientService {
    private final CompanyRepository companyRepository;
    private final CouponRepository couponRepository;
    private final CustomerRepository customerRepository;
    Logger logger = LoggerFactory.getLogger(AdminService.class);

    public boolean login(String email, String password) throws ApplicationException {
        if (email.equals(TestData.ADMIN_EMAIL) & password.equals(TestData.AMDIN_PASSWORD)) {
            logger.info("Administrator logged into the system.");
            return true;
        }
        throw new WrongCertificationsException("Wrong email or password");
    }

    public CustomerDto createCustomer(final CustomerDto customerDto) throws ApplicationException {
        if(!customerRepository.existsByEmail(customerDto.getEmail())){
            customerDto.hashPassword();
            return ObjectMappingUtil.customerEntityToDto(
                    customerRepository.save(
                            ObjectMappingUtil.customerDtoToEntity(customerDto)));
        }
        else {
            throw new ApplicationException("Email already exist in the system.");
        }
    }

    public CompanyDto createCompany(final CompanyDto companyDto) throws ApplicationException {
        if (companyRepository.existsByName(companyDto.getName())){
            throw new ApplicationException("Name already exist in the system.");
        }
        if (companyRepository.existsByEmail(companyDto.getEmail())){
            throw new ApplicationException("Email already exist in the system.");
        }
        else {
            companyDto.hashPassword();
            return ObjectMappingUtil.companyEntityToCompanyDto(
                    companyRepository.save(
                            ObjectMappingUtil.companyDtoToCompanyEntity(companyDto)));
        }
    }

    public void deleteCustomer(final long id) throws ApplicationException{
        if (!customerRepository.existsById(id)){
            throw new EntityNotFoundException(EntityType.CUSTOMER, id);
        }
        logger.info("Customer been deleted, id: "+ id);
        customerRepository.deleteById(id);
    }

    @Transactional
    public void deleteCompany(final long id) throws ApplicationException{
        if (!companyRepository.existsById(id)){
            throw new EntityNotFoundException(EntityType.COMPANY, id);
        }
        couponRepository.deleteAllByCompanyId(id);
        companyRepository.deleteById(id);
        logger.info("Company been deleted, id: "+ id);
    }

    public List<CustomerEntity> getAllCustomers(){
        return customerRepository.findAll();
    }

    public List<CompanyEntity> getAllCompanies(){
        return companyRepository.findAll();
    }

    public CustomerDto getCustomerById(final long id) throws ApplicationException {
        if (!customerRepository.existsById(id)){
            throw new ApplicationException("No customer under the id: "+id);
        }
        return ObjectMappingUtil.customerEntityToDto(customerRepository.findById(id).get());
    }

    public CompanyDto getCompanyById(final long id) throws ApplicationException {
        if (!companyRepository.existsById(id)){
            throw new ApplicationException("No company under the id: "+id);
        }
        return ObjectMappingUtil.companyEntityToCompanyDto(companyRepository.findById(id).get());
    }

    public CustomerDto getCustomerByEmail(final String email) throws ApplicationException {
        if (!customerRepository.existsByEmail(email)){
            throw new ApplicationException("No customer under the email: "+email);
        }
        return ObjectMappingUtil.customerEntityToDto(customerRepository.findByEmail(email).get(0));
    }

    public CompanyDto getCompanyByEmail(final String email) throws ApplicationException {
        if(!companyRepository.existsByEmail(email)){
            throw new ApplicationException("No company under the email: "+email);
        }
        return ObjectMappingUtil.companyEntityToCompanyDto(companyRepository.findByEmail(email).get(0));
    }

    public CompanyDto getCompanyByName(final String name) throws ApplicationException {
        if(!companyRepository.existsByName(name)){
            throw new ApplicationException("No company under the name: "+name);
        }
        return ObjectMappingUtil.companyEntityToCompanyDto(companyRepository.findByName(name).get(0));
    }

}
