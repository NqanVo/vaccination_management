package com.api.vaccinationmanagement.converter;

import com.api.vaccinationmanagement.dto.patient.HistoryVaccinationDto;
import com.api.vaccinationmanagement.model.VMModel;

public class VMModelConverter {
    public static HistoryVaccinationDto VMToHistoryModel(VMModel vmModel){
        return HistoryVaccinationDto
                .builder()
                .id(vmModel.getId())
                .namePatient(vmModel.getPatientModel().getFullname())
                .nameSick(vmModel.getSickModel().getName())
                .nameVaccine(vmModel.getVaccineModel().getName())
                .vaccinationDate(vmModel.getVaccinationDate())
                .build();
    }
}
