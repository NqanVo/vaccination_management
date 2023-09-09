package com.api.vaccinationmanagement.controller;

import com.api.vaccinationmanagement.exception.NotFoundException;
import com.api.vaccinationmanagement.model.HistoryVaccinationModel;
import com.api.vaccinationmanagement.model.PatientModel;
import com.api.vaccinationmanagement.model.VMModel;
import com.api.vaccinationmanagement.response.ResponseModel;
import com.api.vaccinationmanagement.service.PatientService;
import com.api.vaccinationmanagement.service.VMService;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/patients")
public class PatientController {
    @Autowired
    private PatientService patientService;
    @Autowired
    private VMService vmService;

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

        int actualSizePage = (sizePage != null && sizePage > 0) ? sizePage : 10;
        int actualCurrentPage = (currentPage != null && currentPage > 0) ? currentPage : 1;

        Pageable pageable = PageRequest.of(actualCurrentPage - 1, actualSizePage);

        ResponseModel<Page<PatientModel>> responseModel = new ResponseModel<>(
                Timestamp.valueOf(LocalDateTime.now()),
                200,
                "",
                patientService.findByFilters(fullname, email, phone, birthdateFrom, birthdateTo, "0001", pageable));
        return ResponseEntity.ok(responseModel);
    }

    @GetMapping("/{id}/history-vaccination")
    public ResponseEntity<?> findHistoryVaccination(@PathVariable Integer id) throws RuntimeException {

        ResponseModel<List<HistoryVaccinationModel>> responseModel = new ResponseModel<>(
                Timestamp.valueOf(LocalDateTime.now()),
                200,
                "",
                vmService.findHistoryVaccinationByPatient(id));
        return ResponseEntity.ok(responseModel);
    }

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

    @PostMapping
    public ResponseEntity<?> createPatient(@RequestBody PatientModel patientModel) throws RuntimeException {
        try {
            ResponseModel<PatientModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    200,
                    null,
                    patientService.saveNew(patientModel));
            return ResponseEntity.ok(responseModel);
        } catch (RuntimeException ex) {
            ResponseModel<PatientModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    500,
                    ex.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseModel);
        }

    }

    @PutMapping
    public ResponseEntity<?> updatePatient(@RequestBody PatientModel patientModel) throws RuntimeException {
        try {
            ResponseModel<PatientModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    404,
                    null,
                    patientService.saveUpdate(patientModel));
            return ResponseEntity.ok(responseModel);
        } catch (RuntimeException ex) {
            ResponseModel<PatientModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    500,
                    ex.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseModel);
        }
    }

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
