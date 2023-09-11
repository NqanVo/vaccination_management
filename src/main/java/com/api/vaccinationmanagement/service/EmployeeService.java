package com.api.vaccinationmanagement.service;

import com.api.vaccinationmanagement.dto.InputLoginDto;
import com.api.vaccinationmanagement.dto.InputSignUpDto;
import com.api.vaccinationmanagement.dto.OutputEmployeeDto;
import com.api.vaccinationmanagement.dto.OutputLoginDto;
import com.api.vaccinationmanagement.model.EmployeeModel;

public interface EmployeeService extends BaseService<EmployeeModel, EmployeeModel> {
    OutputLoginDto login(InputLoginDto inputLoginDto);

    OutputEmployeeDto signUp(InputSignUpDto inputSignUpDto);

    OutputLoginDto refreshToken(String refreshToken);
}
