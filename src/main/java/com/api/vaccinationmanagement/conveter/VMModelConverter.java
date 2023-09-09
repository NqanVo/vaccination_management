package com.api.vaccinationmanagement.conveter;

import com.api.vaccinationmanagement.model.HistoryVaccinationModel;
import com.api.vaccinationmanagement.model.VMModel;

public class VMModelConverter {
    public static HistoryVaccinationModel VMToHistoryModel(VMModel vmModel){
        return HistoryVaccinationModel
                .builder()
                .id(vmModel.getId())
                .namePatient(vmModel.getPatientModel().getFullname())
                .nameSick(vmModel.getSickModel().getName())
                .nameVaccine(vmModel.getVaccineModel().getName())
                .vaccinationDate(vmModel.getVaccinationDate())
                .build();
    }
}
