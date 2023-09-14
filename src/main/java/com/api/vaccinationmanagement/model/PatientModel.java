package com.api.vaccinationmanagement.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "patient")
@Table(name = "patient")
public class PatientModel extends BaseModel{
    @Column(length = 50)
    private String fullname;
    @Column(length = 50)
    private String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Timestamp birthdate;
    @Column(length = 20)
    private String phone;
    @Column(length = 15)
    private String addressCode;
    @JsonIgnore
    @OneToMany(mappedBy = "patientModel",cascade = CascadeType.ALL)
    private List<VMModel> vmModelList = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "patientModel", cascade = CascadeType.ALL)
    private List<HistorySentEmailModel> historySentEmailModels = new ArrayList<>();
}
