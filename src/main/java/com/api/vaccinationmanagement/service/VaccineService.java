package com.api.vaccinationmanagement.service;

import com.api.vaccinationmanagement.dto.vaccine.InputVaccineDto;
import com.api.vaccinationmanagement.model.VaccineModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VaccineService extends BaseService<VaccineModel, InputVaccineDto>{
    Page<VaccineModel> findByFilters(String name, String madeIn, Integer age, Integer sickId, Pageable pageable);
}
