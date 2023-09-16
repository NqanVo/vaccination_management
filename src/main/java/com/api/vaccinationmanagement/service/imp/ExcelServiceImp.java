package com.api.vaccinationmanagement.service.imp;

import com.api.vaccinationmanagement.converter.PatientConverter;
import com.api.vaccinationmanagement.dto.patient.InputPatientDto;
import com.api.vaccinationmanagement.repository.PatientRepo;
import com.api.vaccinationmanagement.service.ExcelService;
import com.api.vaccinationmanagement.utils.CustomRow;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class ExcelServiceImp implements ExcelService {
    @Autowired
    private PatientRepo patientRepo;

    @Override
    public String processExcelFile(MultipartFile file) throws IOException {
        Set<Row> data = getUnder100Rows(file);
        if (data != null) {
            int totalPatientSuccess = 0;
            int totalPatient = 0;
            Set<Row> rowsUnique = new HashSet<>();
            Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
            // Lap qua tung row, kiem tra, luu gia tri
            for (Row row : data) {
                if (rowsUnique.contains(row)) continue;
                else {
                    try {
                        String fullnameCell = row.getCell(0).getStringCellValue();
                        String emailCell = row.getCell(1).getStringCellValue();
                        Timestamp birthdateCell = numericToTimestamp(row.getCell(2).getNumericCellValue());
                        String phoneCell = row.getCell(3).getStringCellValue();
                        String addressCodeCell = row.getCell(4).getStringCellValue();

                        InputPatientDto inputPatientDto = InputPatientDto
                                .builder()
                                .fullname(fullnameCell)
                                .email(emailCell)
                                .phone(phoneCell)
                                .birthdate(birthdateCell)
                                .addressCode(addressCodeCell)
                                .build();
                        Set<ConstraintViolation<InputPatientDto>> violations = validator.validate(inputPatientDto);

                        if (violations.isEmpty()) {
                            patientRepo.save(PatientConverter.InputToModelCreate(inputPatientDto));
                            totalPatient++;
                            totalPatientSuccess++;
                        } else {
                            totalPatient++;
                            for (ConstraintViolation<InputPatientDto> violation : violations) {
                                log.error(violation.getMessage());
                            }
                        }
                    } catch (Exception ex) {
                        totalPatient++;
                    }
                    rowsUnique.add(row);
                }
            }
            if (totalPatientSuccess == 0) throw new RuntimeException("recheck file, something wrong");
            else return "Create success " + totalPatientSuccess + "/" + totalPatient;
        } else
            throw new RuntimeException("Only accept file excel (*.xls / *.xlsx)");
    }

    @Override
    public boolean isFileExcel(MultipartFile file) {
        try {
            // Kiểm tra phần mở rộng của tệp
            String fileName = file.getOriginalFilename();
            if (fileName != null && (fileName.endsWith(".xlsx") || fileName.endsWith(".xls"))) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private Set<Row> getUnder100Rows(MultipartFile file) {
        if (isFileExcel(file)) {
            String typeFile = file.getOriginalFilename().endsWith(".xlsx") ? ".xlsx" : ".xls";
            try (Workbook workbook = typeFile.equals(".xlsx")
                    ? new XSSFWorkbook(file.getInputStream())
                    : new HSSFWorkbook(file.getInputStream())) {
                // Chọn sheet đầu tiên
                Sheet sheet = workbook.getSheetAt(0);

                int rowCount = 0;
                Set<Row> rowsUnique = new HashSet<>();
                for (Row row : sheet) {
                    if (rowCount > 100) throw new RuntimeException("Only accept under 100 rows");
                    if (row.getCell(0) != null) {
                        rowsUnique.add(row);
                        rowCount++;
                    }
                }
                // Kiểm tra số dòng
                workbook.close();
                return rowsUnique;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private Timestamp numericToTimestamp(double numeric) {
        Date birthdateDate = DateUtil.getJavaDate(numeric);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return Timestamp.valueOf(formatter.format(birthdateDate));
    }
}
