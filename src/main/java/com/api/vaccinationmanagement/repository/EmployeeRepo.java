package com.api.vaccinationmanagement.repository;

import com.api.vaccinationmanagement.model.EmployeeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<EmployeeModel, Integer> {
    Optional<EmployeeModel> findEmployeeModelByEmail(String email);
}
