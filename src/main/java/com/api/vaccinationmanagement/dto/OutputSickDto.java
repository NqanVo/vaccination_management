package com.api.vaccinationmanagement.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OutputSickDto {
    private Integer id;
    private String name;
    private String description;
    private List<OutputVaccineDto> vaccineDtoList = new ArrayList<>();
}
