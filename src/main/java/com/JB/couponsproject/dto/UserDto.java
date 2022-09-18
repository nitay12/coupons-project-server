package com.JB.couponsproject.dto;


import com.JB.couponsproject.enums.Roles;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String password;
    private Roles role;

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
