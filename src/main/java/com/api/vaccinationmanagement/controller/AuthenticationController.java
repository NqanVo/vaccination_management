package com.api.vaccinationmanagement.controller;

import com.api.vaccinationmanagement.dto.employee.InputLoginDto;
import com.api.vaccinationmanagement.dto.employee.InputSignUpDto;
import com.api.vaccinationmanagement.dto.employee.OutputEmployeeDto;
import com.api.vaccinationmanagement.dto.employee.OutputLoginDto;
import com.api.vaccinationmanagement.exception.NotFoundException;
import com.api.vaccinationmanagement.exception.UnAuthorizationException;
import com.api.vaccinationmanagement.response.ResponseModel;
import com.api.vaccinationmanagement.service.EmployeeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/authentication")
@SecurityRequirement(name = "Authorization")
public class AuthenticationController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody InputLoginDto inputLoginDto) throws RuntimeException{
        ResponseModel<OutputLoginDto> responseModel = new ResponseModel<>(
                Timestamp.valueOf(LocalDateTime.now()),
                200,
                null,
                employeeService.login(inputLoginDto));
        return ResponseEntity.ok(responseModel);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody InputSignUpDto inputSignUpDto) throws RuntimeException {
        ResponseModel<OutputEmployeeDto> responseModel = new ResponseModel<>(
                Timestamp.valueOf(LocalDateTime.now()),
                200,
                null,
                employeeService.signUp(inputSignUpDto));
        return ResponseEntity.ok(responseModel);
    }

    @GetMapping("/refresh-token/{token}")
    public ResponseEntity<?> refreshToken(@PathVariable String token) throws RuntimeException {
        ResponseModel<OutputLoginDto> responseModel = new ResponseModel<>(
                Timestamp.valueOf(LocalDateTime.now()),
                200,
                null,
                employeeService.refreshToken(token));
        return ResponseEntity.ok(responseModel);
    }
}
