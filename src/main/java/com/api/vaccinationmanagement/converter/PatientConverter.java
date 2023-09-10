package com.api.vaccinationmanagement.converter;

import com.api.vaccinationmanagement.dto.InputPatientDto;
import com.api.vaccinationmanagement.model.PatientModel;
import org.springframework.beans.BeanUtils;

public class PatientConverter {
    public static PatientModel InputToModelUpdate(InputPatientDto inputPatientDto, PatientModel patientModel) {
        BeanUtils.copyProperties(inputPatientDto, patientModel);
        return patientModel;
    }

    public static PatientModel InputToModelCreate(InputPatientDto inputPatientDto) {
        PatientModel patientModel = new PatientModel();
        BeanUtils.copyProperties(inputPatientDto, patientModel);
        return patientModel;
    }
}
