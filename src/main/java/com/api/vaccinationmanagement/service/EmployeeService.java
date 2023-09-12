package com.api.vaccinationmanagement.service;

import com.api.vaccinationmanagement.dto.employee.*;

import java.util.Optional;

public interface EmployeeService {
    OutputLoginDto login(InputLoginDto inputLoginDto);

    OutputEmployeeDto signUp(InputSignUpDto inputSignUpDto);

    OutputLoginDto refreshToken(String refreshToken);

    Optional<OutputEmployeeDto> findById(Integer id);

    Optional<OutputEmployeeDto> findByEmail(String email);

    OutputEmployeeDto saveUpdate(InputEmployeeUpdateDto dto, String email);

    OutputEmployeeDto saveUpdatePassword(InputChangePasswordDto dto, String email);

    void deleteByEmail(String email);
}
