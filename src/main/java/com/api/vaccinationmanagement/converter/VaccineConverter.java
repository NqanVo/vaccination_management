package com.api.vaccinationmanagement.converter;

import com.api.vaccinationmanagement.dto.InputVaccineDto;
import com.api.vaccinationmanagement.model.VaccineModel;
import org.springframework.beans.BeanUtils;

public class VaccineConverter {
    public static VaccineModel InputToModelUpdate(InputVaccineDto inputVaccineDto, VaccineModel vaccineModel){
        BeanUtils.copyProperties(inputVaccineDto, vaccineModel);
        return vaccineModel;
    }
    public static VaccineModel InputToModelCreate(InputVaccineDto inputVaccineDto){
        VaccineModel vaccineModel = new VaccineModel();
        BeanUtils.copyProperties(inputVaccineDto, vaccineModel);
        return vaccineModel;
    }
}
