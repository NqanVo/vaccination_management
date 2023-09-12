package com.api.vaccinationmanagement.controller;

import com.api.vaccinationmanagement.dto.sick.InputSickDto;
import com.api.vaccinationmanagement.dto.sick.OutputSickDto;
import com.api.vaccinationmanagement.exception.NotFoundException;
import com.api.vaccinationmanagement.model.PatientModel;
import com.api.vaccinationmanagement.model.SickModel;
import com.api.vaccinationmanagement.response.ResponseModel;
import com.api.vaccinationmanagement.service.SickService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

@RestController
@RequestMapping("/sicks")
@SecurityRequirement(name = "Authorization")
public class SickController {
    @Autowired
    private SickService sickService;

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYEE')")
    @GetMapping
    public ResponseEntity<?> findByFilters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer sizePage,
            @RequestParam(required = false) Integer currentPage) throws RuntimeException {

        int actualSizePage = (sizePage != null && sizePage > 0) ? sizePage : 10;
        int actualCurrentPage = (currentPage != null && currentPage > 0) ? currentPage : 1;

        Pageable pageable = PageRequest.of(actualCurrentPage - 1, actualSizePage);

        ResponseModel<Page<OutputSickDto>> responseModel = new ResponseModel<>(
                Timestamp.valueOf(LocalDateTime.now()),
                200,
                null,
                sickService.findByFilters(name, pageable));
        return ResponseEntity.ok(responseModel);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYEE')")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) throws RuntimeException {
        ResponseModel<OutputSickDto> responseModel = new ResponseModel<>(
                Timestamp.valueOf(LocalDateTime.now()),
                200,
                null,
                sickService.findById(id).get());
        return ResponseEntity.ok(responseModel);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PostMapping
    public ResponseEntity<?> createSick(@RequestBody InputSickDto sickModel) throws RuntimeException {
        ResponseModel<OutputSickDto> responseModel = new ResponseModel<>(
                Timestamp.valueOf(LocalDateTime.now()),
                200,
                null,
                sickService.saveNew(sickModel));
        return ResponseEntity.ok(responseModel);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PutMapping
    public ResponseEntity<?> updateSick(@RequestBody InputSickDto sickModel) throws RuntimeException {
        ResponseModel<OutputSickDto> responseModel = new ResponseModel<>(
                Timestamp.valueOf(LocalDateTime.now()),
                200,
                null,
                sickService.saveUpdate(sickModel));
        return ResponseEntity.ok(responseModel);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) throws RuntimeException {
        sickService.deleteById(id);
        ResponseModel<SickModel> responseModel = new ResponseModel<>(
                Timestamp.valueOf(LocalDateTime.now()),
                200,
                "Delete success sick with id: " + id,
                null);
        return ResponseEntity.ok(responseModel);
    }
}
