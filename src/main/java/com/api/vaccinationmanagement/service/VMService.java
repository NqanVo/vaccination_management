package com.api.vaccinationmanagement.service;

import com.api.vaccinationmanagement.dto.HistoryVaccinationDto;
import com.api.vaccinationmanagement.dto.InputVMDto;
import com.api.vaccinationmanagement.model.VMModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.List;

public interface VMService extends BaseService<VMModel, InputVMDto> {
    Page<VMModel> findByFilters(Integer patientId, Integer sickId,
                                Integer vaccineId, Timestamp vaccinationFrom,
                                Timestamp vaccinationTo, String addressCode, Pageable pageable);

    List<HistoryVaccinationDto> findHistoryVaccinationByPatient(Integer id);
    VMModel saveNew(InputVMDto vmModel);
    VMModel saveUpdate(InputVMDto vmModel);
}
