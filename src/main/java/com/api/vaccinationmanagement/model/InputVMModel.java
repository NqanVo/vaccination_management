package com.api.vaccinationmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class InputVMModel {
    private Integer vmId;
    private Integer patientId;
    private Integer vaccineId;
    private Integer sickId;
    private Timestamp vaccinationDate;
}
