package com.api.vaccinationmanagement.service;

import com.api.vaccinationmanagement.dto.InputSickDto;
import com.api.vaccinationmanagement.model.SickModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SickService extends BaseService<SickModel, InputSickDto> {

    Page<SickModel> findByFilters(String name, Pageable pageable);
}
