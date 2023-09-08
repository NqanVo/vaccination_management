package com.api.vaccinationmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "vaccine")
@Table(name = "vaccine")
public class VaccineModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(columnDefinition = "LONGTEXT")
    private String description;
    private String madeIn;
    private Integer minAge;
    private Integer maxAge;
    @ManyToOne
    @JoinColumn(name = "sick_id")
    private SickModel sickModel;
    @OneToMany(mappedBy = "vaccineModel",cascade = CascadeType.ALL)
    private List<VMModel> vmModelList = new ArrayList<>();
}
