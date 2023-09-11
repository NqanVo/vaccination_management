package com.api.vaccinationmanagement.controller;

import com.api.vaccinationmanagement.dto.InputPatientDto;
import com.api.vaccinationmanagement.exception.NotFoundException;
import com.api.vaccinationmanagement.dto.HistoryVaccinationDto;
import com.api.vaccinationmanagement.exception.UnAuthorizationException;
import com.api.vaccinationmanagement.model.PatientModel;
import com.api.vaccinationmanagement.response.ResponseModel;
import com.api.vaccinationmanagement.service.PatientService;
import com.api.vaccinationmanagement.service.VMService;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {
    @Autowired
    private PatientService patientService;
    @Autowired
    private VMService vmService;

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYEE')")
    @GetMapping
    public ResponseEntity<?> findByFilters(
            @RequestParam(required = false) String fullname,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String addressCode,
            @RequestParam(required = false) @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC") Timestamp birthdateFrom,
            @RequestParam(required = false) @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC") Timestamp birthdateTo,
            @RequestParam(required = false) Integer sizePage,
            @RequestParam(required = false) Integer currentPage) throws RuntimeException {

        try {
            int actualSizePage = (sizePage != null && sizePage > 0) ? sizePage : 10;
            int actualCurrentPage = (currentPage != null && currentPage > 0) ? currentPage : 1;

            Pageable pageable = PageRequest.of(actualCurrentPage - 1, actualSizePage);

            ResponseModel<Page<PatientModel>> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    200,
                    "",
                    patientService.findByFilters(fullname, email, phone, birthdateFrom, birthdateTo, addressCode, pageable));
            return ResponseEntity.ok(responseModel);
        } catch (UnAuthorizationException ex) {
            ResponseModel<PatientModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    401,
                    ex.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseModel);
        } catch (RuntimeException ex) {
            ResponseModel<PatientModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    500,
                    ex.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseModel);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYEE')")
    @GetMapping("/{id}/history-vaccination")
    public ResponseEntity<?> findHistoryVaccination(@PathVariable Integer id) throws RuntimeException {
        try {
            ResponseModel<List<HistoryVaccinationDto>> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    200,
                    "",
                    vmService.findHistoryVaccinationByPatient(id));
            return ResponseEntity.ok(responseModel);
        } catch (NotFoundException ex) {
            ResponseModel<PatientModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    404,
                    ex.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseModel);
        } catch (RuntimeException ex) {
            ResponseModel<PatientModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    500,
                    ex.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseModel);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYEE')")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) throws RuntimeException {
        try {
            ResponseModel<PatientModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    200,
                    null,
                    patientService.findById(id).get());
            return ResponseEntity.ok(responseModel);
        } catch (NotFoundException ex) {
            ResponseModel<PatientModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    404,
                    ex.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseModel);
        } catch (RuntimeException ex) {
            ResponseModel<PatientModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    500,
                    ex.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseModel);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYEE')")
    @PostMapping
    public ResponseEntity<?> createPatient(@RequestBody InputPatientDto patientModel) throws RuntimeException {
        try {
            ResponseModel<PatientModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    200,
                    null,
                    patientService.saveNew(patientModel));
            return ResponseEntity.ok(responseModel);
        } catch (UnAuthorizationException ex) {
            ResponseModel<PatientModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    401,
                    ex.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseModel);
        } catch (RuntimeException ex) {
            ResponseModel<PatientModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    500,
                    ex.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseModel);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYEE')")
    @PutMapping
    public ResponseEntity<?> updatePatient(@RequestBody InputPatientDto patientModel) throws RuntimeException {
        try {
            ResponseModel<PatientModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    200,
                    null,
                    patientService.saveUpdate(patientModel));
            return ResponseEntity.ok(responseModel);
        } catch (UnAuthorizationException ex) {
            ResponseModel<PatientModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    401,
                    ex.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseModel);
        } catch (RuntimeException ex) {
            ResponseModel<PatientModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    500,
                    ex.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseModel);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) throws RuntimeException {
        try {
            patientService.deleteById(id);
            ResponseModel<PatientModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    404,
                    "Delete success patient with id: " + id,
                    null);
            return ResponseEntity.ok(responseModel);
        } catch (NotFoundException ex) {
            ResponseModel<PatientModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    404,
                    ex.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseModel);
        } catch (RuntimeException ex) {
            ResponseModel<PatientModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    500,
                    ex.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseModel);
        }
    }
}
