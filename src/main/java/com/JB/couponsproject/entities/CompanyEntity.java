package com.JB.couponsproject.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@NoArgsConstructor
public class CompanyEntity {

    public CompanyEntity(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password.hashCode();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name", nullable = false)
    private String name;
    @Column(name="email", nullable = false)
    private String email;
    @Column(name="password", nullable = false)
    private int password;


}
