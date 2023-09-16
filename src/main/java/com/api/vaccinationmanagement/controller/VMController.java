package com.api.vaccinationmanagement.controller;

import com.api.vaccinationmanagement.dto.statistical.StatisticalRateVaccinatedBySickIdAndRegionDto;
import com.api.vaccinationmanagement.dto.statistical.StatisticalRateVaccinatedEachSick;
import com.api.vaccinationmanagement.dto.InputVMDto;
import com.api.vaccinationmanagement.model.PatientModel;
import com.api.vaccinationmanagement.model.VMModel;
import com.api.vaccinationmanagement.response.ResponseModel;
import com.api.vaccinationmanagement.service.VMService;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestController
@RequestMapping("vaccination-management")
@SecurityRequirement(name = "Authorization")
public class VMController {
    @Autowired
    private VMService vmService;

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYEE')")
    @GetMapping
    public ResponseEntity<?> findByFilters(
            @RequestParam(required = false) Integer patientId,
            @RequestParam(required = false) Integer sickId,
            @RequestParam(required = false) Integer vaccineId,
            @RequestParam(required = false) String addressCode,
            @RequestParam(required = false) @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC") Timestamp vaccinationFrom,
            @RequestParam(required = false) @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC") Timestamp vaccinationTo,
            @RequestParam(required = false) Integer sizePage,
            @RequestParam(required = false) Integer currentPage
    ) throws RuntimeException {
        int actualSizePage = (sizePage != null && sizePage > 0) ? sizePage : 10;
        int actualCurrentPage = (currentPage != null && currentPage > 0) ? currentPage : 1;

        Pageable pageable = PageRequest.of(actualCurrentPage - 1, actualSizePage);

        ResponseModel<Page<VMModel>> responseModel = new ResponseModel<>(
                Timestamp.valueOf(LocalDateTime.now()),
                200,
                null,
                vmService.findByFilters(patientId, sickId, vaccineId, vaccinationFrom, vaccinationTo, addressCode, pageable));
        return ResponseEntity.ok(responseModel);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYEE')")
    @GetMapping("/find-patient-vaccinated-with-sick-id-and-min-doses-and-region")
    public ResponseEntity<?> findPatientVaccinatedWithSickIdAndMinDosesAndAddressCode(
            @RequestParam Integer sickId,
            @RequestParam Integer minDoses,
            @RequestParam(required = false)
            @Pattern(regexp = "^(\\d{4}|\\d{4}-\\d{4}|\\d{4}-\\d{4}-\\d{4})$", message = "Invalid roleRegion format")
            String addressCode,
            @RequestParam(required = false) Integer sizePage,
            @RequestParam(required = false) Integer currentPage
    ) {
        int actualSizePage = (sizePage != null && sizePage > 0) ? sizePage : 10;
        int actualCurrentPage = (currentPage != null && currentPage > 0) ? currentPage : 1;

        Pageable pageable = PageRequest.of(actualCurrentPage - 1, actualSizePage);

        ResponseModel<Page<PatientModel>> responseModel = new ResponseModel<>(
                Timestamp.valueOf(LocalDateTime.now()),
                200,
                null,
                vmService.findPatientVaccinatedWithSickIdAndMinDosesAndAddressCode(sickId, minDoses, addressCode, pageable));
        return ResponseEntity.ok(responseModel);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping("/find-rate-patient-vaccinated-each-sick")
    public ResponseEntity<?> findRatePatientVaccinatedEachSick() {
        ResponseModel<StatisticalRateVaccinatedEachSick> responseModel = new ResponseModel<>(
                Timestamp.valueOf(LocalDateTime.now()),
                200,
                null,
                vmService.findRatePatientVaccinatedEachSick());
        return ResponseEntity.ok(responseModel);
    }
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping("find-rate-patient-vaccinated-by-sick-id-and-region")
    public ResponseEntity<?> findRatePatientVaccinatedBySickIdAndRegion(
            @RequestParam Integer sickId,
            @Pattern(regexp = "^(\\d{4}|\\d{4}-\\d{4}|\\d{4}-\\d{4}-\\d{4})$", message = "Invalid roleRegion format")
            @RequestParam String addressCode
    ){
        ResponseModel<StatisticalRateVaccinatedBySickIdAndRegionDto> responseModel = new ResponseModel<>(
                Timestamp.valueOf(LocalDateTime.now()),
                200,
                null,
                vmService.findRatePatientVaccinatedBySickIdAndRegion(sickId,addressCode));
        return ResponseEntity.ok(responseModel);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYEE')")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) throws RuntimeException {
        ResponseModel<VMModel> responseModel = new ResponseModel<>(
                Timestamp.valueOf(LocalDateTime.now()),
                200,
                null,
                vmService.findById(id).get());
        return ResponseEntity.ok(responseModel);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYEE')")
    @PostMapping
    public ResponseEntity<?> createVM(@RequestBody InputVMDto vmModel) throws RuntimeException {
        ResponseModel<VMModel> responseModel = new ResponseModel<>(
                Timestamp.valueOf(LocalDateTime.now()),
                200,
                null,
                vmService.saveNew(vmModel));
        return ResponseEntity.ok(responseModel);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYEE')")
    @PutMapping
    public ResponseEntity<?> updateVM(@RequestBody InputVMDto vmModel) throws RuntimeException {
        ResponseModel<VMModel> responseModel = new ResponseModel<>(
                Timestamp.valueOf(LocalDateTime.now()),
                200,
                null,
                vmService.saveUpdate(vmModel));
        return ResponseEntity.ok(responseModel);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) throws RuntimeException {
        vmService.deleteById(id);
        ResponseModel<VMModel> responseModel = new ResponseModel<>(
                Timestamp.valueOf(LocalDateTime.now()),
                200,
                "Delete success vaccination management with id: " + id,
                null);
        return ResponseEntity.ok(responseModel);
    }
}
