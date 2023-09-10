package com.api.vaccinationmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "vaccination_management")
@Table(name = "vaccination_management")
public class VMModel extends BaseModel{
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientModel patientModel;
    @ManyToOne
    @JoinColumn(name = "sick_id")
    private SickModel sickModel;
    @ManyToOne
    @JoinColumn(name = "vaccine_id")
    private VaccineModel vaccineModel;
    private Timestamp vaccinationDate;
}
