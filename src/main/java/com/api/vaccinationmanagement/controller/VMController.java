package com.api.vaccinationmanagement.controller;

import com.api.vaccinationmanagement.exception.NotFoundException;
import com.api.vaccinationmanagement.model.InputVMModel;
import com.api.vaccinationmanagement.model.VMModel;
import com.api.vaccinationmanagement.response.ResponseModel;
import com.api.vaccinationmanagement.service.VMService;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestController
@RequestMapping("vaccination-management")
public class VMController {
    @Autowired
    private VMService vmService;

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
    ) {
        try {
            int actualSizePage = (sizePage != null && sizePage > 0) ? sizePage : 10;
            int actualCurrentPage = (currentPage != null && currentPage > 0) ? currentPage : 1;

            Pageable pageable = PageRequest.of(actualCurrentPage - 1, actualSizePage);

            ResponseModel<Page<VMModel>> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    200,
                    null,
                    vmService.findByFilters(patientId, sickId, vaccineId, vaccinationFrom, vaccinationTo, addressCode, pageable));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseModel);
        } catch (RuntimeException ex) {
            ResponseModel<VMModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    500,
                    ex.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseModel);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try {
            ResponseModel<VMModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    200,
                    null,
                    vmService.findById(id).get());
            return ResponseEntity.ok(responseModel);
        } catch (NotFoundException ex) {
            ResponseModel<VMModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    404,
                    ex.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseModel);
        } catch (RuntimeException ex) {
            ResponseModel<VMModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    500,
                    ex.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseModel);
        }
    }

    @PostMapping
    public ResponseEntity<?> createVM(@RequestBody InputVMModel vmModel) {
        try {
            ResponseModel<VMModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    200,
                    null,
                    vmService.saveNew(vmModel));
            return ResponseEntity.ok(responseModel);
        } catch (NotFoundException ex) {
            ResponseModel<VMModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    404,
                    ex.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseModel);
        } catch (RuntimeException ex) {
            ResponseModel<VMModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    500,
                    ex.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseModel);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateVM(@RequestBody InputVMModel vmModel) {
        try {
            ResponseModel<VMModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    200,
                    null,
                    vmService.saveUpdate(vmModel));
            return ResponseEntity.ok(responseModel);
        } catch (NotFoundException ex) {
            ResponseModel<VMModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    404,
                    ex.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseModel);
        } catch (RuntimeException ex) {
            ResponseModel<VMModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    500,
                    ex.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseModel);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) {
        try {
            vmService.deleteById(id);
            ResponseModel<VMModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    200,
                    "Delete success vaccination management with id: " + id,
                    null);
            return ResponseEntity.ok(responseModel);
        } catch (NotFoundException ex) {
            ResponseModel<VMModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    404,
                    ex.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseModel);
        } catch (RuntimeException ex) {
            ResponseModel<VMModel> responseModel = new ResponseModel<>(
                    Timestamp.valueOf(LocalDateTime.now()),
                    500,
                    ex.getMessage(),
                    null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseModel);
        }
    }
}
