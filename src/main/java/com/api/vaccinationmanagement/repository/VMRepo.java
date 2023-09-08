package com.api.vaccinationmanagement.repository;

import com.api.vaccinationmanagement.model.PatientModel;
import com.api.vaccinationmanagement.model.SickModel;
import com.api.vaccinationmanagement.model.VMModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VMRepo extends JpaRepository<VMModel, Integer> {
    List<VMModel> findVMModelsByPatientModel(PatientModel patientModel);
    List<VMModel> findVMModelsBySickModel(SickModel sickModel);
    List<VMModel> findVMModelsByPatientModelAndSickModel(PatientModel patientModel, SickModel sickModel);
}
