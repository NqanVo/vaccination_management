package com.api.vaccinationmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InputSickDto {
    private Integer id;
    private String name;
    private String description;
}
