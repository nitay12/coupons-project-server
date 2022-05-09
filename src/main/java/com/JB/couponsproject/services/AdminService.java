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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final CompanyRepository companyRepository;
    private final CouponRepository couponRepository;
    private final CustomerRepository customerRepository;
    Logger logger = LoggerFactory.getLogger(AdminService.class);

    public void login(String email, String password) throws ApplicationException {
        if (email.equals(Texts.ADMIN_EMAIL) & password.equals(Texts.AMDIN_PASSWORD)) {
            logger.info("Administrator logged into the system.");
            return;
        }
        throw new WrongCertificationsException("Wrong email or password");
    }

    public CustomerDto createCustomer(final CustomerDto customerDto) throws ApplicationException {
        if(!customerRepository.existsByEmail(customerDto.getEmail())){
            final CustomerEntity customerEntity = ObjectMappingUtil.customerDtoToEntity(customerDto);
            return ObjectMappingUtil.customerEntityToDto(customerRepository.save(customerEntity));
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
            final CompanyEntity companyEntity = ObjectMappingUtil.companyDtoToCompanyEntity(companyDto);
            return ObjectMappingUtil.companyEntityToCompanyDto(companyRepository.save(companyEntity));
        }
    }

    /*public void updateCustomer(CustomerDto customerDto) throws ApplicationException{
        List<CustomerEntity> customerEntities = customerRepository.finalAllById(customerDto.getId());
        if(customerEntities.size()>=Texts.LISTSIZE){
            throw new ApplicationException("Cannot update customer, duplicated customer id: "+ customerDto.getId());
        }
        else{
            final CustomerEntity customerEntity = ObjectMappingUtil.customerDtoToEntity(customerDto);
            customerRepository.save(customerEntity);
            logger.info("Customer "+ customerDto.getId() +" was updated");
        }
    }*/

    public void updateCompany(CompanyDto companyDto) throws ApplicationException{
        List<CompanyEntity> companyEntities = companyRepository.findAllById(companyDto.getId());
        if (companyEntities.size()>=Texts.LISTSIZE){
            throw new ApplicationException("Cannot update company, duplicated company id: "+ companyDto.getId());
        }
        else {
            final CompanyEntity companyEntity = ObjectMappingUtil.companyDtoToCompanyEntity(companyDto);
            companyRepository.save(companyEntity);
            logger.info("Company "+ companyDto.getId() +" was updated");
        }
    }

    public void deleteCustomer(CustomerDto customerDto) throws ApplicationException{
        if (!customerRepository.existsById(customerDto.getId())){
            throw new EntityNotFoundException(EntityType.customer, customerDto.getId());
        }
        logger.info("Customer been deleted, id: "+customerDto.getId());
        customerRepository.deleteById(customerDto.getId());
    }
@Transactional
    public void deleteCompany(CompanyDto companyDto) throws ApplicationException{
        if (!companyRepository.existsById(companyDto.getId())){
            throw new EntityNotFoundException(EntityType.company, companyDto.getId());
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

    public CustomerEntity getCustomerById(Long id){
        return customerRepository.findById(id).get();
    }

    public CompanyEntity getCompanyById(Long id){
        return companyRepository.findById(id).get();
    }

}
