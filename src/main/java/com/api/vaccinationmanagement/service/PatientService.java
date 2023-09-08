package com.api.vaccinationmanagement.service;

import com.api.vaccinationmanagement.model.PatientModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PatientService extends BaseService<PatientModel>{
    List<PatientModel> findByEmail(String email);

    Page<PatientModel> findByRoleRegion(Pageable pageable);
}
