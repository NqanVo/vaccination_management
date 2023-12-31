package com.api.vaccinationmanagement.controller;

import com.api.vaccinationmanagement.dto.patient.InputExcelPatientDto;
import com.api.vaccinationmanagement.dto.patient.InputPatientDto;
import com.api.vaccinationmanagement.dto.patient.HistoryVaccinationDto;
import com.api.vaccinationmanagement.dto.patient.InputSentEmailPatientDto;
import com.api.vaccinationmanagement.model.PatientModel;
import com.api.vaccinationmanagement.response.ResponseModel;
import com.api.vaccinationmanagement.service.ExcelService;
import com.api.vaccinationmanagement.service.PatientService;
import com.api.vaccinationmanagement.service.VMService;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/patients")
@SecurityRequirement(name = "Authorization")
public class PatientController {
    @Autowired
    private PatientService patientService;
    @Autowired
    private VMService vmService;
    @Autowired
    private ExcelService excelService;

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYEE')")
    @GetMapping
    public ResponseEntity<?> findByFilters(
            @RequestParam(required = false) String fullname,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String addressCode,
            @RequestParam(required = false) @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC") Timestamp birthdateFrom,
            @RequestParam(required = false) @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC") Timestamp birthdateTo,
            @RequestParam(required = false) Integer ageFrom,
            @RequestParam(required = false) Integer ageTo,
            @RequestParam(required = false) Integer sizePage,
            @RequestParam(required = false) Integer currentPage) throws RuntimeException {

        int actualSizePage = (sizePage != null && sizePage > 0) ? sizePage : 10;
        int actualCurrentPage = (currentPage != null && currentPage > 0) ? currentPage : 1;

        Pageable pageable = PageRequest.of(actualCurrentPage - 1, actualSizePage);

        ResponseModel<Page<PatientModel>> responseModel = new ResponseModel<>(
                Timestamp.valueOf(LocalDateTime.now()),
                200,
                "",
                patientService.findByFilters(fullname, email, phone, birthdateFrom, birthdateTo, ageFrom, ageTo, addressCode, pageable));
        return ResponseEntity.ok(responseModel);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYEE')")
    @GetMapping("/{id}/history-vaccination")
    public ResponseEntity<?> findHistoryVaccination(@PathVariable Integer id) throws RuntimeException {
        ResponseModel<List<HistoryVaccinationDto>> responseModel = new ResponseModel<>(
                Timestamp.valueOf(LocalDateTime.now()),
                200,
                "",
                vmService.findHistoryVaccinationByPatient(id));
        return ResponseEntity.ok(responseModel);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYEE')")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) throws RuntimeException {
        ResponseModel<PatientModel> responseModel = new ResponseModel<>(
                Timestamp.valueOf(LocalDateTime.now()),
                200,
                null,
                patientService.findById(id).get());
        return ResponseEntity.ok(responseModel);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYEE')")
    @PostMapping
    public ResponseEntity<?> createPatient(@RequestBody @Valid InputPatientDto patientModel) throws RuntimeException {
        ResponseModel<PatientModel> responseModel = new ResponseModel<>(
                Timestamp.valueOf(LocalDateTime.now()),
                200,
                null,
                patientService.saveNew(patientModel));
        return ResponseEntity.ok(responseModel);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYEE')")
    @PostMapping(value = "/excel")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(
            mediaType = "multipart/form-data",
            schema = @Schema(implementation = InputExcelPatientDto.class)
    ))
    public ResponseEntity<?> createPatientWithExcel(@RequestPart(name = "file") MultipartFile file) throws RuntimeException, IOException {
        ResponseModel<String> responseModel = new ResponseModel<>(
                Timestamp.valueOf(LocalDateTime.now()),
                200,
                excelService.processExcelFile(file),
                null);
        return ResponseEntity.ok(responseModel);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYEE')")
    @PutMapping
    public ResponseEntity<?> updatePatient(@RequestBody @Valid InputPatientDto patientModel) throws RuntimeException {
        ResponseModel<PatientModel> responseModel = new ResponseModel<>(
                Timestamp.valueOf(LocalDateTime.now()),
                200,
                null,
                patientService.saveUpdate(patientModel));
        return ResponseEntity.ok(responseModel);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) throws RuntimeException {
        patientService.deleteById(id);
        ResponseModel<PatientModel> responseModel = new ResponseModel<>(
                Timestamp.valueOf(LocalDateTime.now()),
                404,
                "Delete success patient with id: " + id,
                null);
        return ResponseEntity.ok(responseModel);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYEE')")
    @PostMapping("/sent-email")
    public ResponseEntity<?> sentEmail(@RequestBody @Valid InputSentEmailPatientDto inputSentEmailPatientDto) {
        ResponseModel<String> responseModel = new ResponseModel<>(
                Timestamp.valueOf(LocalDateTime.now()),
                200,
                patientService.sendEmailToPatients(inputSentEmailPatientDto.getTitle(), inputSentEmailPatientDto.getMessage(), inputSentEmailPatientDto.getListPatientId()),
                null);
        return ResponseEntity.ok(responseModel);
    }

}
