package com.api.vaccinationmanagement.repository;

import com.api.vaccinationmanagement.model.SickModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SickRepo extends JpaRepository<SickModel, Integer> {
    Page<SickModel> findSickModelsByNameLike(String name, Pageable pageable);
    Optional<SickModel> findSickModelByName(String name);
}
