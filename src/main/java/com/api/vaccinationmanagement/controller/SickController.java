package com.api.vaccinationmanagement.controller;

import com.api.vaccinationmanagement.dto.InputSickDto;
import com.api.vaccinationmanagement.exception.NotFoundException;
import com.api.vaccinationmanagement.model.PatientModel;
import com.api.vaccinationmanagement.model.SickModel;
import com.api.vaccinationmanagement.response.ResponseModel;
import com.api.vaccinationmanagement.service.SickService;
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
public class SickController {
    @Autowired
    private SickService sickService;

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYEE')")
    @GetMapping
    public ResponseEntity<?> findByFilters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer sizePage,
            @RequestParam(required = false) Integer currentPage) {
        try {
            int actualSizePage = (sizePage != null && sizePage > 0) ? sizePage : 10;
            int actualCurrentPage = (currentPage != null && currentPage > 0) ? currentPage : 1;

            Pageable pageable = PageRequest.of(actualCurrentPage - 1, actualSizePage);

            ResponseModel<Page<SickModel>> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    200,
                    null,
                    sickService.findByFilters(name, pageable));
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

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYEE')")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) throws RuntimeException {
        try {
            ResponseModel<SickModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    200,
                    null,
                    sickService.findById(id).get());
            return ResponseEntity.ok(responseModel);
        } catch (NotFoundException ex) {
            ResponseModel<SickModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    404,
                    ex.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseModel);
        } catch (RuntimeException ex) {
            ResponseModel<SickModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    500,
                    ex.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseModel);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PostMapping
    public ResponseEntity<?> createSick(@RequestBody InputSickDto sickModel) {
        try {
            ResponseModel<SickModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    200,
                    null,
                    sickService.saveNew(sickModel));
            return ResponseEntity.ok(responseModel);
        } catch (RuntimeException ex) {
            ResponseModel<SickModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    500,
                    ex.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseModel);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PutMapping
    public ResponseEntity<?> updateSick(@RequestBody InputSickDto sickModel) {
        try {
            ResponseModel<SickModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    200,
                    null,
                    sickService.saveUpdate(sickModel));
            return ResponseEntity.ok(responseModel);
        } catch (NotFoundException ex) {
            ResponseModel<SickModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    404,
                    ex.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseModel);
        } catch (RuntimeException ex) {
            ResponseModel<SickModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    500,
                    ex.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseModel);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) {
        try {
            sickService.deleteById(id);
            ResponseModel<SickModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    200,
                    "Delete success sick with id: " + id,
                    null);
            return ResponseEntity.ok(responseModel);
        } catch (NotFoundException ex) {
            ResponseModel<SickModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    404,
                    ex.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseModel);
        } catch (RuntimeException ex) {
            ResponseModel<SickModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    500,
                    ex.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseModel);
        }
    }
}
