package com.JB.couponsproject.dto;


import com.JB.couponsproject.entities.CompanyEntity;
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

    public CompanyEntity toEntity() {
        return CompanyEntity.builder()
                .id(this.id)
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .build();
    }

    public void hashPassword() {
        setPassword(String.valueOf(password.hashCode()));
    }
}
