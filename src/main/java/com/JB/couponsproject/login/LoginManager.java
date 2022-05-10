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

    public void login(int loginType,String email,String password){
        switch (loginType){
            case 1:
                clientService = adminService;
                break;
            case 2:
                clientService = companyService;
                break;
            case 3:
                clientService = customerService;
                break;
            default:
                break;
        }
        try{
            if(clientService.login(email,password)){
                users.put(email,UserType.values()[loginType -1]);
                System.out.println("welcome " + email);
            }
        }catch (ApplicationException e){
            System.out.println(e.getMessage());
        }
    }

    public Map<String, UserType> getUsers() {
        return users;
    }

    public void logout(String email){
        users.remove(email);
    }
}
