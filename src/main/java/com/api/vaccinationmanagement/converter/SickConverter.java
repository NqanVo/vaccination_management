package com.api.vaccinationmanagement.converter;

import com.api.vaccinationmanagement.dto.sick.InputSickDto;
import com.api.vaccinationmanagement.dto.sick.OutputSickDto;
import com.api.vaccinationmanagement.dto.vaccine.OutputVaccineDto;
import com.api.vaccinationmanagement.model.SickModel;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class SickConverter {
    public static SickModel InputToModelUpdate(InputSickDto inputSickDto, SickModel sickModel) {
        BeanUtils.copyProperties(inputSickDto, sickModel);
        return sickModel;
    }

    public static SickModel InputToModelCreate(InputSickDto inputSickDto) {
        SickModel sickModel = new SickModel();
        BeanUtils.copyProperties(inputSickDto, sickModel);
        return sickModel;
    }

    public static OutputSickDto ModelToOutput(SickModel sickModel) {
        OutputSickDto outputSickDto = new OutputSickDto();
        BeanUtils.copyProperties(sickModel, outputSickDto);
        List<OutputVaccineDto> outputVaccineDtoList = new ArrayList<>();
        sickModel.getVaccineModelList().forEach(vaccine -> outputVaccineDtoList.add(VaccineConverter.ModelToOutput(vaccine)));
        outputSickDto.setVaccineDtoList(outputVaccineDtoList);
        return outputSickDto;
    }
}
