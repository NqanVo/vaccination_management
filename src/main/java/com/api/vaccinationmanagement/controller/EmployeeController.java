package com.api.vaccinationmanagement.controller;

import com.api.vaccinationmanagement.dto.employee.InputChangePasswordDto;
import com.api.vaccinationmanagement.dto.employee.InputEmployeeUpdateDto;
import com.api.vaccinationmanagement.dto.employee.OutputEmployeeDto;
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
import java.util.Optional;

@RestController
@RequestMapping("/employees")
@SecurityRequirement(name = "Authorization")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYEE')")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) throws RuntimeException{
        ResponseModel<Optional<OutputEmployeeDto>> responseModel = new ResponseModel<>(
                Timestamp.valueOf(LocalDateTime.now()),
                200,
                null,
                employeeService.findById(id));
        return ResponseEntity.ok(responseModel);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYEE')")
    @GetMapping("/email/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable String email) throws RuntimeException {
        ResponseModel<Optional<OutputEmployeeDto>> responseModel = new ResponseModel<>(
                Timestamp.valueOf(LocalDateTime.now()),
                200,
                null,
                employeeService.findByEmail(email));
        return ResponseEntity.ok(responseModel);
    }

    @PutMapping("/{email}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYEE') and #email == authentication.name or hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<?> updateEmployee(@PathVariable String email, @RequestBody InputEmployeeUpdateDto dto) throws RuntimeException {
        ResponseModel<OutputEmployeeDto> responseModel = new ResponseModel<>(
                Timestamp.valueOf(LocalDateTime.now()),
                200,
                null,
                employeeService.saveUpdate(dto, email));
        return ResponseEntity.ok(responseModel);
    }

    @PutMapping("/{email}/change-password")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYEE') and #email == authentication.name or hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<?> updatePassword(@PathVariable String email, @RequestBody InputChangePasswordDto dto) throws RuntimeException {
        ResponseModel<OutputEmployeeDto> responseModel = new ResponseModel<>(
                Timestamp.valueOf(LocalDateTime.now()),
                200,
                null,
                employeeService.saveUpdatePassword(dto, email));
        return ResponseEntity.ok(responseModel);
    }

    @DeleteMapping("/{email}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> deleteByEmail(@PathVariable String email) throws RuntimeException {
        employeeService.deleteByEmail(email);
        ResponseModel<OutputEmployeeDto> responseModel = new ResponseModel<>(
                Timestamp.valueOf(LocalDateTime.now()),
                200,
                "Delete success employee with email: " + email,
                null);
        return ResponseEntity.ok(responseModel);
    }

}
