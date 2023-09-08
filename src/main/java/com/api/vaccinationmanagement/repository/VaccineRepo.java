package com.api.vaccinationmanagement.repository;

import com.api.vaccinationmanagement.model.VaccineModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccineRepo extends JpaRepository<VaccineModel, Integer> {
}
