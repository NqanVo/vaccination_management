package com.api.vaccinationmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class HistoryEmailDto {
    private String title;
    private String content;
    private Timestamp sentAt;
    private String employeeEmail;
    private String patientName;
}
