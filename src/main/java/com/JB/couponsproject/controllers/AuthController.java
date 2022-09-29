package com.JB.couponsproject.controllers;

import com.JB.couponsproject.exceptions.ApplicationException;
import com.JB.couponsproject.login.LoginManager;
import com.JB.couponsproject.dto.ClientLoginDto;
import com.JB.couponsproject.security.JwtWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {
    private final LoginManager loginManager;

    @PostMapping("login")
    public JwtWrapper login(@RequestBody ClientLoginDto client) throws ApplicationException {
        return loginManager.login(client.getUserType(), client.getEmail(), client.getPassword());
    }
}
