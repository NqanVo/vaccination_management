package com.api.vaccinationmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryVaccinationDto {
    private Integer id;
    private String namePatient;
    private String nameSick;
    private String nameVaccine;
    private Timestamp vaccinationDate;
}
