package com.JB.couponsproject.login;

import com.JB.couponsproject.enums.UserType;
import com.JB.couponsproject.exceptions.ApplicationException;
import com.JB.couponsproject.services.AdminService;
import com.JB.couponsproject.services.ClientService;
import com.JB.couponsproject.services.CompanyService;
import com.JB.couponsproject.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class LoginManager {

    private final AdminService adminService;
    private final CompanyService companyService;
    private final CustomerService customerService;

    private final Map<String, UserType> users = new HashMap<>();
    private ClientService clientService;

    public boolean login(UserType userType, String email, String password) throws ApplicationException {
        switch (userType) {
            case ADMIN -> clientService = adminService;
            case COMPANY -> clientService = companyService;
            case CUSTOMER -> clientService = customerService;
            default -> {
            }
        }
        if (clientService.login(email, password)) {
            users.put(email, userType);
            return true;
        }
        else {
            return false;
        }
    }

    public Map<String, UserType> getUsers() {
        return users;
    }

    public void logout(String email) {
        users.remove(email);
    }
}
