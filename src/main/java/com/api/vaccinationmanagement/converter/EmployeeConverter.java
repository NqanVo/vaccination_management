package com.api.vaccinationmanagement.converter;

import com.api.vaccinationmanagement.dto.employee.InputEmployeeUpdateDto;
import com.api.vaccinationmanagement.dto.employee.InputSignUpDto;
import com.api.vaccinationmanagement.dto.employee.OutputEmployeeDto;
import com.api.vaccinationmanagement.model.EmployeeModel;
import org.springframework.beans.BeanUtils;

public class EmployeeConverter {
    public static EmployeeModel InputToModelCreate(InputSignUpDto inputSignUpDto) {
        EmployeeModel employeeModel = new EmployeeModel();
        BeanUtils.copyProperties(inputSignUpDto, employeeModel);
        return employeeModel;
    }

    public static EmployeeModel InputToModelUpdate(InputEmployeeUpdateDto dto, EmployeeModel employeeModel) {
        BeanUtils.copyProperties(dto, employeeModel);
        return employeeModel;
    }

    public static OutputEmployeeDto ModelToOutput(EmployeeModel model) {
        OutputEmployeeDto outputEmployeeDto = new OutputEmployeeDto();
        BeanUtils.copyProperties(model, outputEmployeeDto);
        return outputEmployeeDto;
    }
}
