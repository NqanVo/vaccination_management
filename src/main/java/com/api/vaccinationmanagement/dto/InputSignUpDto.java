package com.api.vaccinationmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InputSignUpDto {
    private Integer id;
    private String fullname;
    private String email;
    private String password;
    private String roleRegion;
    private Integer roleId;
}
