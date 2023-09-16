package com.api.vaccinationmanagement.converter;

import com.api.vaccinationmanagement.dto.HistoryEmailDto;
import com.api.vaccinationmanagement.model.HistorySentEmailModel;
import org.springframework.beans.BeanUtils;

public class EmailConverter {
    public static HistoryEmailDto ModelToHistory(HistorySentEmailModel model){
        HistoryEmailDto historyEmailDto = new HistoryEmailDto();
        BeanUtils.copyProperties(model, historyEmailDto);
        historyEmailDto.setEmployeeEmail(model.getEmployeeModel().getEmail());
        historyEmailDto.setPatientName(model.getPatientModel().getFullname());
        return historyEmailDto;
    }
}
