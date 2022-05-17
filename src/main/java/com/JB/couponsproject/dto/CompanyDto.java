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
    private final String name;
    private String email;
    private String password;

}
