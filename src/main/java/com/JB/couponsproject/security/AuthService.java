package com.JB.couponsproject.security;

import com.JB.couponsproject.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    public JwtWrapper login(final ClientService loginClientService){
        return new JwtWrapper(JwtUtil.generateToken(loginClientService.getEmail(), loginClientService.getUserType()));
    }
}
