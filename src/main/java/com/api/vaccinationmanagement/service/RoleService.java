package com.api.vaccinationmanagement.service;

import com.api.vaccinationmanagement.model.RoleModel;

import java.util.Optional;

public interface RoleService {
    Optional<RoleModel> findById(Integer id);
    Optional<RoleModel> findByCode(String code);
    String getRoleRegionFromJwt();
    String getRoleRegionForSet(String addressCodeRequest);
    String getRoleRegionForGet(String addressCodeRequest);
    String getRole();
    boolean isAdmin();
    boolean isManager();
    boolean isEmployee();
}
