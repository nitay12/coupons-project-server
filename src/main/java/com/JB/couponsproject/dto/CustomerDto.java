package com.JB.couponsproject.dto;

import lombok.*;

/**
 * A data transfer object for the customer entity
 */
@Builder
@AllArgsConstructor
@ToString
@Data
@RequiredArgsConstructor
public class CustomerDto {
    private final long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public void hashPassword(){
        setPassword(String.valueOf(password.hashCode()));
    }
}
