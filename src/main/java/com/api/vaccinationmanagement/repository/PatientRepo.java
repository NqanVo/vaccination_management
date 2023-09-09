package com.api.vaccinationmanagement.repository;

import com.api.vaccinationmanagement.model.PatientModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepo extends JpaRepository<PatientModel, Integer> {
    List<PatientModel> findPatientModelsByEmail(String email);

    Page<PatientModel> findPatientModelsByAddressCodeLike(Pageable pageable, String region);
}
