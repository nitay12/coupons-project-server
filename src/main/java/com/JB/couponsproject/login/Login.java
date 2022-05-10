package com.JB.couponsproject.login;

import com.JB.couponsproject.texts.Texts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class Login {
    private final Scanner SCANNER = new Scanner(System.in);

    private final LoginManager loginManager;
    public void login(){
        System.out.println("Are you admin, company or a n customer? 1,2,3");
        int loginType = SCANNER.nextInt();
        System.out.println("Please enter your email: ");
        String email = SCANNER.next();
        System.out.println("Please enter your password: ");
        String password = SCANNER.next();
        loginManager.login(loginType,email,password);
    }

    public void askLogout(String email){
        System.out.println("Do you want to log out of " + email + "? : Y/N ");
        String answer = SCANNER.next();
        if(answer.equals("Y")){
            loginManager.logout(email);
        }
    }
}
