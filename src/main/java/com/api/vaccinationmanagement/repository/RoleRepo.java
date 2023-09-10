package com.api.vaccinationmanagement.repository;

import com.api.vaccinationmanagement.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<RoleModel, Integer> {
    Optional<RoleModel> findRoleModelByCode(String code);
}
