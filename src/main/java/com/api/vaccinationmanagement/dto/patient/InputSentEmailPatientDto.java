package com.api.vaccinationmanagement.dto.patient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputSentEmailPatientDto {
    private String title;
    private String message;
    private List<Integer> listPatientId = new ArrayList<>();
}
