package com.api.vaccinationmanagement.service;

import com.api.vaccinationmanagement.dto.HistoryEmailDto;
import com.api.vaccinationmanagement.dto.employee.*;
import com.api.vaccinationmanagement.model.HistorySentEmailModel;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    OutputLoginDto login(InputLoginDto inputLoginDto);

    OutputEmployeeDto signUp(InputSignUpDto inputSignUpDto);

    OutputLoginDto refreshToken(String refreshToken);

    List<HistoryEmailDto> findHistoryEmail(String email);

    Optional<OutputEmployeeDto> findById(Integer id);

    Optional<OutputEmployeeDto> findByEmail(String email);

    OutputEmployeeDto saveUpdate(InputEmployeeUpdateDto dto, String email);

    OutputEmployeeDto saveUpdatePassword(InputChangePasswordDto dto, String email);

    void deleteByEmail(String email);
}
