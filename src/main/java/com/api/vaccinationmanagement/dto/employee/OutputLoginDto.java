package com.api.vaccinationmanagement.dto.employee;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OutputLoginDto {
    private String accessToken;
    private String refreshToken;
}
