package com.api.vaccinationmanagement.repository;

import com.api.vaccinationmanagement.model.SickModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SickRepo extends JpaRepository<SickModel, Integer> {
}
