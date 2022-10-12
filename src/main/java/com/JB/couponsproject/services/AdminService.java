package com.JB.couponsproject.services;

import com.JB.couponsproject.constants.TestData;
import com.JB.couponsproject.dto.CompanyDto;
import com.JB.couponsproject.dto.CustomerDto;
import com.JB.couponsproject.entities.CompanyEntity;
import com.JB.couponsproject.entities.CustomerEntity;
import com.JB.couponsproject.enums.EntityType;
import com.JB.couponsproject.enums.UserType;
import com.JB.couponsproject.exceptions.ApplicationException;
import com.JB.couponsproject.exceptions.EntityNotFoundException;
import com.JB.couponsproject.exceptions.UpdateException;
import com.JB.couponsproject.exceptions.WrongCredentialsException;
import com.JB.couponsproject.repositories.CompanyRepository;
import com.JB.couponsproject.repositories.CouponRepository;
import com.JB.couponsproject.repositories.CustomerRepository;
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

    @Override
    public long getId() {
        return 0;
    }

    private final UserType userType = UserType.ADMIN;

    public boolean login(String email, String password) throws ApplicationException {
        if (email.equals(TestData.ADMIN_LOGIN_EMAIL) & password.equals(TestData.AMDIN_LOGIN_PASSWORD)) {
            logger.info("Administrator logged into the system.");
            return true;
        }
        throw new WrongCredentialsException("Wrong email or password");
    }

    @Override
    public UserType getUserType() {
        return userType;
    }

    @Override
    public String getEmail() {
        return TestData.ADMIN_LOGIN_EMAIL;
    }

    public CustomerDto createCustomer(final CustomerDto customerDto) throws ApplicationException {
        if (!customerRepository.existsByEmail(customerDto.getEmail())) {
            customerDto.hashPassword();
            return customerRepository.save(customerDto.toEntity()).toDto();
        } else {
            throw new ApplicationException("Email already exist in the system.");
        }
    }

    public CompanyDto createCompany(final CompanyDto companyDto) throws ApplicationException {
        if (companyRepository.existsByName(companyDto.getName())) {
            throw new ApplicationException("Name already exist in the system.");
        }
        if (companyRepository.existsByEmail(companyDto.getEmail())) {
            throw new ApplicationException("Email already exist in the system.");
        } else {
            companyDto.hashPassword();
            return companyRepository.save(companyDto.toEntity()).toDto();
        }
    }

    public void deleteCustomer(final long id) throws ApplicationException {
        if (!customerRepository.existsById(id)) {
            throw new EntityNotFoundException(EntityType.CUSTOMER, id);
        }
        logger.info("Customer been deleted, id: " + id);
        customerRepository.deleteById(id);
    }

    @Transactional
    public void deleteCompany(final long id) throws ApplicationException {
        if (!companyRepository.existsById(id)) {
            throw new EntityNotFoundException(EntityType.COMPANY, id);
        }
        couponRepository.deleteAllByCompanyId(id);
        companyRepository.deleteById(id);
        logger.info("Company been deleted, id: " + id);
    }

    public List<CustomerEntity> getAllCustomers() {
        return customerRepository.findAll();
    }

    public List<CompanyEntity> getAllCompanies() {
        return companyRepository.findAll();
    }

    public CustomerDto getCustomerById(final long id) throws ApplicationException {
        if (!customerRepository.existsById(id)) {
            throw new ApplicationException("No customer under the id: " + id);
        }
        return customerRepository.findById(id).get().toDto();
    }

    public CompanyDto getCompanyById(final long id) throws ApplicationException {
        if (!companyRepository.existsById(id)) {
            throw new ApplicationException("No company under the id: " + id);
        }
        return companyRepository.findById(id).get().toDto();
    }

    public CustomerDto getCustomerByEmail(final String email) throws ApplicationException {
        if (!customerRepository.existsByEmail(email)) {
            throw new ApplicationException("No customer under the email: " + email);
        }
        return customerRepository.findByEmail(email).get(0).toDto();
    }

    public CompanyDto getCompanyByEmail(final String email) throws ApplicationException {
        if (!companyRepository.existsByEmail(email)) {
            throw new ApplicationException("No company under the email: " + email);
        }
        return companyRepository.findByEmail(email).get(0).toDto();
    }

    public CompanyDto getCompanyByName(final String name) throws ApplicationException {
        if (!companyRepository.existsByName(name)) {
            throw new ApplicationException("No company under the name: " + name);
        }
        return companyRepository.findByName(name).get(0).toDto();
    }

    public CompanyDto updateCompany(CompanyDto company) throws EntityNotFoundException, UpdateException {
        if (!companyRepository.existsById(company.getId())) {
            throw new EntityNotFoundException(EntityType.COMPANY, company.getId());
        }
        if (companyRepository.existsByName(company.getName())) {
            throw new UpdateException("Name already exist in the system.");
        }
        if (companyRepository.existsByEmail(company.getEmail())) {
            throw new UpdateException("Email already exist in the system.");
        }
        final CompanyEntity updatedCompany = companyRepository.saveAndFlush(company.toEntity());
        return updatedCompany.toDto();
    }

    public CustomerDto updateCustomer(CustomerDto customer) throws UpdateException {
        if (!customerRepository.existsByEmail(customer.getEmail())) {
            return customerRepository
                    .save(customer.toEntity())
                    .toDto();
        } else {
            throw new UpdateException("Email already exist in the system.");
        }
    }
}
