package com.JB.couponsproject.login;

import com.JB.couponsproject.enums.UserType;
import com.JB.couponsproject.exceptions.ApplicationException;
import com.JB.couponsproject.security.AuthService;
import com.JB.couponsproject.security.JwtUtil;
import com.JB.couponsproject.security.JwtWrapper;
import com.JB.couponsproject.services.AdminService;
import com.JB.couponsproject.services.ClientService;
import com.JB.couponsproject.services.CompanyService;
import com.JB.couponsproject.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.JB.couponsproject.constants.TestData.ADMIN_LOGIN_EMAIL;
import static com.JB.couponsproject.constants.TestData.AMDIN_LOGIN_PASSWORD;

@Component
@RequiredArgsConstructor
public class LoginManager {
    private final AuthService authService;
    private final AdminService adminService;
    private final CompanyService companyService;
    private final CustomerService customerService;
    private ClientService clientService;

    public JwtWrapper login(UserType userType, String email, String password) throws ApplicationException {
        switch (userType) {
            case ADMIN -> clientService = adminService;
            case COMPANY -> clientService = companyService;
            case CUSTOMER -> clientService = customerService;
        }
        //Admin credentials hard coded
        if (clientService.getUserType().equals(UserType.ADMIN) && (password.equals(AMDIN_LOGIN_PASSWORD) && email.equals(ADMIN_LOGIN_EMAIL))) {
            return new JwtWrapper(JwtUtil.generateToken(0L, ADMIN_LOGIN_EMAIL, clientService.getUserType()));
        } else if (clientService.login(email, password)) {
            return authService.login(clientService);
        } else {
            throw new ApplicationException(); //check for other options
        }
    }
}
