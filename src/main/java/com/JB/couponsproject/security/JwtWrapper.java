package com.JB.couponsproject.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class JwtWrapper {
    private String jwtToken;

    public String getJwtToken() {
        if (jwtToken.startsWith("Bearer ")) {
            jwtToken = jwtToken.substring(7);
        }
        return jwtToken;
    }
}
