package com.JB.couponsproject.tests;

import com.JB.couponsproject.enums.UserType;
import com.JB.couponsproject.login.Login;
import com.JB.couponsproject.login.LoginManager;
import com.JB.couponsproject.services.AdminService;
import com.JB.couponsproject.services.CompanyService;
import com.JB.couponsproject.services.CustomerService;
import com.JB.couponsproject.texts.Texts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.w3c.dom.Text;

@Component
@RequiredArgsConstructor
public class Tests {

    private final LoginManager loginManager;
    private final Login login;
    private final AdminService adminService;
    private final CompanyService companyService;
    private final CustomerService customerService;


    // add tests
    public void test(int id, UserType userType){
        switch (userType){
            case ADMIN:
                if(loginManager.getUsers().containsKey(Texts.ADMIN_EMAIL)
                        && loginManager.getUsers().get(Texts.ADMIN_EMAIL) == userType){

                }
                //login.askLogout();
                break;

            case COMPANY:
                //....add find email by is through service
                //login.askLogout();
                break;

            case CUSTOMER:
                //... add find email by is through service

                //login.askLogout();
                break;
            default:
                break;
        }

    }
}
