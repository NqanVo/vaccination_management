package com.api.vaccinationmanagement.dto.vaccine;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OutputVaccineDto {
    private Integer id;
    private String name;
    private String description;
    private String madeIn;
    private Integer minAge;
    private Integer maxAge;
}
