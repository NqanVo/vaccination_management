package com.api.vaccinationmanagement.service;

import com.api.vaccinationmanagement.dto.sick.InputSickDto;
import com.api.vaccinationmanagement.dto.sick.OutputSickDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SickService extends BaseService<OutputSickDto, InputSickDto> {

    Page<OutputSickDto> findByFilters(String name, Pageable pageable);
}
