package com.JB.couponsproject.dto;

import com.JB.couponsproject.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClientLoginDto {
    UserType userType;
    String email;
    String password;
}
