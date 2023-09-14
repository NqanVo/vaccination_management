package com.api.vaccinationmanagement.repository;

import com.api.vaccinationmanagement.model.EmployeeModel;
import com.api.vaccinationmanagement.model.HistorySentEmailModel;
import com.api.vaccinationmanagement.model.PatientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HistorySentEmailRepo extends JpaRepository<HistorySentEmailModel, Integer> {
    Optional<HistorySentEmailModel> findHistorySentEmailsByPatientModel(PatientModel patientModel);
    Optional<HistorySentEmailModel> findHistorySentEmailsByEmployeeModel(EmployeeModel patientModel);
}
