package com.api.vaccinationmanagement.converter;

import com.api.vaccinationmanagement.dto.InputSickDto;
import com.api.vaccinationmanagement.model.SickModel;
import org.springframework.beans.BeanUtils;

public class SickConverter {
    public static SickModel InputToModelUpdate(InputSickDto inputSickDto, SickModel sickModel){
        BeanUtils.copyProperties(inputSickDto, sickModel);
        return sickModel;
    }

    public static SickModel InputToModelCreate(InputSickDto inputSickDto){
        SickModel sickModel = new SickModel();
        BeanUtils.copyProperties(inputSickDto, sickModel);
        return sickModel;
    }
}
