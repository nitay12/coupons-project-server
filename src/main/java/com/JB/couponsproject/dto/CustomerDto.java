package com.JB.couponsproject.dto;

import lombok.*;

/**
 * A data transfer object for the customer entity
 */
@Builder
@AllArgsConstructor
@ToString
@RequiredArgsConstructor
public class CustomerDto {
    private final long id;
    private String firstName;
    private String lastName;
    private String email;
    private int password;

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }
}
