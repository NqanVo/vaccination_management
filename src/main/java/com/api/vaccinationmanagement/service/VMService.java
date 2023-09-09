package com.api.vaccinationmanagement.service;

import com.api.vaccinationmanagement.model.HistoryVaccinationModel;
import com.api.vaccinationmanagement.model.InputVMModel;
import com.api.vaccinationmanagement.model.VMModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface VMService extends BaseService<VMModel> {
    Page<VMModel> findByFilters(Integer patientId, Integer sickId,
                                Integer vaccineId, Timestamp vaccinationFrom,
                                Timestamp vaccinationTo, String addressCode, Pageable pageable);

    List<HistoryVaccinationModel> findHistoryVaccinationByPatient(Integer id);
    VMModel saveNew(InputVMModel vmModel);
    VMModel saveUpdate(InputVMModel vmModel);
}
