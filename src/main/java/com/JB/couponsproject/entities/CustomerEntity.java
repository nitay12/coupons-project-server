package com.JB.couponsproject.entities;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@NoArgsConstructor
public class CustomerEntity {
    public CustomerEntity(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password.hashCode();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="firstName", nullable = false)
    private String firstName;
    @Column(name="lastName", nullable = false)
    private String lastName;
    @Column(name="email", nullable = false)
    private String email;
    @Column(name="password", nullable = false)
    private int password;
}
