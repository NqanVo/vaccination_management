package com.api.vaccinationmanagement.service;

import com.api.vaccinationmanagement.dto.patient.HistoryVaccinationDto;
import com.api.vaccinationmanagement.dto.InputVMDto;
import com.api.vaccinationmanagement.dto.statistical.StatisticalRateVaccinatedBySickIdAndRegionDto;
import com.api.vaccinationmanagement.dto.statistical.StatisticalRateVaccinatedEachSick;
import com.api.vaccinationmanagement.model.PatientModel;
import com.api.vaccinationmanagement.model.VMModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.List;

public interface VMService extends BaseService<VMModel, InputVMDto> {
    Page<VMModel> findByFilters(Integer patientId, Integer sickId,
                                Integer vaccineId, Timestamp vaccinationFrom,
                                Timestamp vaccinationTo, String addressCode, Pageable pageable);

    Page<PatientModel> findPatientVaccinatedWithSickIdAndMinDosesAndAddressCode(Integer sickId, Integer minDoses, String addressCode, Pageable pageable);

    StatisticalRateVaccinatedEachSick findRatePatientVaccinatedEachSick();

    StatisticalRateVaccinatedBySickIdAndRegionDto findRatePatientVaccinatedBySickIdAndRegion(Integer sickId, String addressCode);

    List<HistoryVaccinationDto> findHistoryVaccinationByPatient(Integer id);
    VMModel saveNew(InputVMDto vmModel);
    VMModel saveUpdate(InputVMDto vmModel);
}
