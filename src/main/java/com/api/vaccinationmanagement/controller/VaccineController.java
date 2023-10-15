package com.api.vaccinationmanagement.controller;

import com.api.vaccinationmanagement.dto.vaccine.InputVaccineDto;
import com.api.vaccinationmanagement.exception.NotFoundException;
import com.api.vaccinationmanagement.model.VaccineModel;
import com.api.vaccinationmanagement.response.ResponseModel;
import com.api.vaccinationmanagement.service.VaccineService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
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
@RequestMapping("/vaccines")
@SecurityRequirement(name = "Authorization")
public class VaccineController {
    @Autowired
    private VaccineService vaccineService;

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYEE')")
    @GetMapping
    public ResponseEntity<?> findByFilters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String madeIn,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) Integer sickId,
            @RequestParam(required = false) Integer sizePage,
            @RequestParam(required = false) Integer currentPage
    ) throws RuntimeException {
        int actualSizePage = (sizePage != null && sizePage > 0) ? sizePage : 10;
        int actualCurrentPage = (currentPage != null && currentPage > 0) ? currentPage : 1;

        Pageable pageable = PageRequest.of(actualCurrentPage - 1, actualSizePage);

        ResponseModel<Page<VaccineModel>> responseModel = new ResponseModel<>(
                Timestamp.valueOf(LocalDateTime.now()),
                200,
                null,
                vaccineService.findByFilters(name, madeIn, age, sickId, pageable));
        return ResponseEntity.ok(responseModel);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','EMPLOYEE')")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) throws RuntimeException {
        ResponseModel<VaccineModel> responseModel = new ResponseModel<>(
                Timestamp.valueOf(LocalDateTime.now()),
                200,
                null,
                vaccineService.findById(id).get());
        return ResponseEntity.ok(responseModel);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PostMapping
    public ResponseEntity<?> createVaccine(@RequestBody @Valid InputVaccineDto vaccineModel) throws RuntimeException {
        ResponseModel<VaccineModel> responseModel = new ResponseModel<>(
                Timestamp.valueOf(LocalDateTime.now()),
                200,
                null,
                vaccineService.saveNew(vaccineModel));
        return ResponseEntity.ok(responseModel);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PutMapping
    public ResponseEntity<?> updateVaccine(@RequestBody @Valid InputVaccineDto vaccineModel) throws RuntimeException {
        ResponseModel<VaccineModel> responseModel = new ResponseModel<>(
                Timestamp.valueOf(LocalDateTime.now()),
                200,
                null,
                vaccineService.saveUpdate(vaccineModel));
        return ResponseEntity.ok(responseModel);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) throws RuntimeException {
        vaccineService.deleteById(id);
        ResponseModel<VaccineModel> responseModel = new ResponseModel<>(
                Timestamp.valueOf(LocalDateTime.now()),
                200,
                "Delete success vaccine with id: " + id,
                null);
        return ResponseEntity.ok(responseModel);
    }

}
