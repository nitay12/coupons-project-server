package com.JB.couponsproject.dto;


import lombok.*;

/**
 * A data transfer object for the company entity
 */
@Builder
@Data
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class CompanyDto {
    private final long id;
    private String name;
    private String email;
    private String password;


    public void hashPassword(){
        setPassword(String.valueOf(password.hashCode()));
    }
}
