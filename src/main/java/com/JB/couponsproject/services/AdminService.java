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
import com.JB.couponsproject.texts.Texts;
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
        if (email.equals(Texts.ADMIN_EMAIL) & password.equals(Texts.AMDIN_PASSWORD)) {
            logger.info("Administrator logged into the system.");
            return true;
        }
        throw new WrongCertificationsException("Wrong email or password");
    }

    public CustomerEntity createCustomer(final CustomerEntity customerEntity) throws ApplicationException {
        if(!customerRepository.existsByEmail(customerEntity.getEmail())){
            customerEntity.hashPassword();
            return customerRepository.save(customerEntity);
        }
        else {
            throw new ApplicationException("Email already exist in the system.");
        }
    }

    public CompanyEntity createCompany(final CompanyEntity companyEntity) throws ApplicationException {
        if (companyRepository.existsByName(companyEntity.getName())){
            throw new ApplicationException("Name already exist in the system.");
        }
        if (companyRepository.existsByEmail(companyEntity.getEmail())){
            throw new ApplicationException("Email already exist in the system.");
        }
        else {
            companyEntity.hashPassword();
            return companyRepository.save(companyEntity);
        }
    }

    public void updateCustomer(CustomerDto customerDto) throws ApplicationException {
        final CustomerEntity customerEntity = ObjectMappingUtil.customerDtoToEntity(customerDto);
        customerRepository.save(customerEntity);
        logger.info("Customer " + customerDto.getId() + " was updated");
    }

    public void updateCompany(CompanyDto companyDto) throws ApplicationException{
        final CompanyEntity companyEntity = ObjectMappingUtil.companyDtoToCompanyEntity(companyDto);
        companyRepository.save(companyEntity);
        logger.info("Company "+ companyDto.getId() +" was updated");
    }

    public void deleteCustomer(CustomerDto customerDto) throws ApplicationException{
        if (!customerRepository.existsById(customerDto.getId())){
            throw new EntityNotFoundException(EntityType.CUSTOMER, customerDto.getId());
        }
        logger.info("Customer been deleted, id: "+customerDto.getId());
        customerRepository.deleteById(customerDto.getId());
    }

    @Transactional
    public void deleteCompany(CompanyDto companyDto) throws ApplicationException{
        if (!companyRepository.existsById(companyDto.getId())){
            throw new EntityNotFoundException(EntityType.COMPANY, companyDto.getId());
        }
        couponRepository.deleteAllByCompanyId(companyDto.getId());
        companyRepository.deleteById(companyDto.getId());
        logger.info("Company been deleted, id: "+companyDto.getId());
    }

    public List<CustomerEntity> getAllCustomers(){
        return customerRepository.findAll();
    }

    public List<CompanyEntity> getAllCompanies(){
        return companyRepository.findAll();
    }

    public CustomerDto getCustomerById(Long id) throws ApplicationException {
        if (!customerRepository.existsById(id)){
            throw new ApplicationException("No customer under the id: "+id);
        }
        return ObjectMappingUtil.customerEntityToDto(customerRepository.findById(id).get());
    }

    public CompanyDto getCompanyById(Long id) throws ApplicationException {
        if (!companyRepository.existsById(id)){
            throw new ApplicationException("No company under the id: "+id);
        }
        return ObjectMappingUtil.companyEntityToCompanyDto(companyRepository.findById(id).get());
    }

    public CustomerDto getCustomerByEmail(String email) throws ApplicationException {
        if (!customerRepository.existsByEmail(email)){
            throw new ApplicationException("No customer under the email: "+email);
        }
        return ObjectMappingUtil.customerEntityToDto(customerRepository.findByEmail(email).get(0));
    }

    public CompanyDto getCompanyByEmail(String email) throws ApplicationException {
        if(!companyRepository.existsByEmail(email)){
            throw new ApplicationException("No company under the email: "+email);
        }
        return ObjectMappingUtil.companyEntityToCompanyDto(companyRepository.findByEmail(email).get(0));
    }

    public CompanyDto getCompanyByName(String name) throws ApplicationException {
        if(!companyRepository.existsByName(name)){
            throw new ApplicationException("No company under the name: "+name);
        }
        return ObjectMappingUtil.companyEntityToCompanyDto(companyRepository.findByName(name).get(0));
    }

}
