package com.api.vaccinationmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity(name = "history_sent_email")
@Table(name = "history_sent_email")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistorySentEmailModel extends BaseModel{
    @Column(length = 200)
    private String title;
//    @Column(columnDefinition = "LONGTEXT")
    @Column(columnDefinition = "TEXT") //PostgreSQL
    private String content;
    private Timestamp sentAt;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private EmployeeModel employeeModel;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientModel patientModel;
}
