package com.api.vaccinationmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "sick")
@Table(name = "sick")
public class SickModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(columnDefinition = "LONGTEXT")
    private String description;
    @JsonIgnore
    @OneToMany(mappedBy = "sickModel",cascade = CascadeType.ALL)
    private List<VaccineModel> vaccineModelList = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "sickModel", cascade = CascadeType.ALL)
    private List<VMModel> vmModelList = new ArrayList<>();
}
