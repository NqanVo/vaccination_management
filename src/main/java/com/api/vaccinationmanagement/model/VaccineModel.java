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
@Entity(name = "vaccine")
@Table(name = "vaccine")
public class VaccineModel extends BaseModel {
    @Column(length = 50)
    private String name;
    @Column(columnDefinition = "LONGTEXT")
    private String description;
    @Column(length = 20)
    private String madeIn;
    private Integer minAge;
    private Integer maxAge;
    @ManyToOne
    @JoinColumn(name = "sick_id")
    private SickModel sickModel;
    @JsonIgnore
    @OneToMany(mappedBy = "vaccineModel",cascade = CascadeType.ALL)
    private List<VMModel> vmModelList = new ArrayList<>();
}
