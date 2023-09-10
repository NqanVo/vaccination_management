package com.api.vaccinationmanagement.controller;

import com.api.vaccinationmanagement.dto.InputLoginDto;
import com.api.vaccinationmanagement.dto.InputSignUpDto;
import com.api.vaccinationmanagement.dto.OutputLoginDto;
import com.api.vaccinationmanagement.exception.NotFoundException;
import com.api.vaccinationmanagement.model.EmployeeModel;
import com.api.vaccinationmanagement.response.ResponseModel;
import com.api.vaccinationmanagement.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/authentication")
@Slf4j
public class AuthenticationController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody InputLoginDto inputLoginDto) {
        try {
            ResponseModel<OutputLoginDto> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    200,
                    null,
                    employeeService.login(inputLoginDto));
            return ResponseEntity.ok(responseModel);
        } catch (NotFoundException ex) {
            ResponseModel<OutputLoginDto> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    404,
                    ex.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseModel);
        } catch (RuntimeException ex) {
            ResponseModel<OutputLoginDto> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    500,
                    ex.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseModel);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody InputSignUpDto InputSignUpDto) {
        try {
            ResponseModel<EmployeeModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    200,
                    null,
                    null);
            return ResponseEntity.ok(responseModel);
        } catch (NotFoundException ex) {
            ResponseModel<EmployeeModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    404,
                    ex.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseModel);
        } catch (RuntimeException ex) {
            ResponseModel<EmployeeModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    500,
                    ex.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseModel);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYEE')")
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken() {
        return null;
    }
}
