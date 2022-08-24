package com.JB.couponsproject.login;

import com.JB.couponsproject.enums.UserType;
import com.JB.couponsproject.exceptions.ApplicationException;
import com.JB.couponsproject.security.AuthService;
import com.JB.couponsproject.security.JwtWrapper;
import com.JB.couponsproject.services.AdminService;
import com.JB.couponsproject.services.ClientService;
import com.JB.couponsproject.services.CompanyService;
import com.JB.couponsproject.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginManager {
    private final AuthService authService;
    private final AdminService adminService;
    private final CompanyService companyService;
    private final CustomerService customerService;
    //    private final Map<String, UserType> users = new HashMap<>();
    private ClientService clientService;

    public boolean login(UserType userType, String email, String password) throws ApplicationException {
        switch (userType) {
            case ADMIN -> clientService = adminService;
            case COMPANY -> clientService = companyService;
            case CUSTOMER -> clientService = customerService;
            }

        return clientService.login(email, password);
    }

    public JwtWrapper jwtLogin(UserType userType, String email, String password) throws ApplicationException {
        switch (userType) {
            case ADMIN -> clientService = adminService;
            case COMPANY -> clientService = companyService;
            case CUSTOMER -> clientService = customerService;
        }
        if (clientService.login(email, password)){
            return authService.login(clientService);
        }
        else {
            throw new ApplicationException(); //check for other options
        }
    }
}
