package com.JB.couponsproject.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A data transfer object for the company entity
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto {
    private long id;
    private String name;
    private String email;
    private int password;
}
