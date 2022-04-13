package com.JB.couponsproject.dto;

import lombok.*;

/**
 * A data transfer object for the customer entity
 */
@Data
@Builder
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class CustomerDto {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private int password;

    public CustomerDto(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password.hashCode();
    }
}
