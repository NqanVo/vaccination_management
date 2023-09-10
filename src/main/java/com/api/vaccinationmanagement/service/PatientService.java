package com.api.vaccinationmanagement.service;

import com.api.vaccinationmanagement.dto.InputPatientDto;
import com.api.vaccinationmanagement.model.PatientModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface PatientService extends BaseService<PatientModel, InputPatientDto>{
    Page<PatientModel> findByFilters(String fullname, String email, String phone, Timestamp birthdateFrom, Timestamp birthdateTo, String addressCode, Pageable pageable);
}
