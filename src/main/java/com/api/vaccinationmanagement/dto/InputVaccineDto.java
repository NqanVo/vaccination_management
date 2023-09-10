package com.api.vaccinationmanagement.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InputVaccineDto {
    private Integer id;
    private String name;
    private String description;
    private String madeIn;
    private Integer minAge;
    private Integer maxAge;
    private Integer sickId;
}
