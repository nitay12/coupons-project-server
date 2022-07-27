package com.JB.couponsproject.entities;

import com.JB.couponsproject.dto.CompanyDto;
import lombok.*;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "companies")
public class CompanyEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private long id;

    @Column(name = "name", nullable = false, unique = true)
    @Getter
    @Setter
    private String name;

    @Email
    @Column(name = "email", nullable = false)
    @Getter
    @Setter
    private String email;
    @Column(name = "password", nullable = false)
    @Getter
    @Setter
    private String password;

    public CompanyDto toDto() {
        return CompanyDto.builder()
                .id(this.id)
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .build();
    }
}
