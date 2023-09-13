package com.api.vaccinationmanagement.service.imp;

import com.api.vaccinationmanagement.converter.PatientConverter;
import com.api.vaccinationmanagement.dto.patient.InputPatientDto;
import com.api.vaccinationmanagement.repository.PatientRepo;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@Service
@Slf4j
public class ExcelService {
    @Autowired
    private PatientRepo patientRepo;

    public String processExcelFile(MultipartFile file) throws IOException {
        if (isFileExcel(file)) {
            if (isUnder100Rows(file)) {
                try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
                    Sheet sheet = workbook.getSheetAt(0);
                    int totalPatientSuccess = 0;
                    int totalPatient = 0;
                    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

                    for (Row row : sheet) {
                        try {
                            String fullnameCell = row.getCell(0).getStringCellValue();
                            String emailCell = row.getCell(1).getStringCellValue();
                            String phoneCell = row.getCell(3).getStringCellValue();
                            String addressCodeCell = row.getCell(4).getStringCellValue();
                            Timestamp birthdateCell = numericToTimestamp(row.getCell(2).getNumericCellValue());

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

                    }
                    return totalPatientSuccess == 0 ? "recheck file, something wrong" : "Create success " + totalPatientSuccess + "/" + totalPatient;
                } catch (Exception ex) {
                    throw new RuntimeException("Something wrong");
                }
            } else
                throw new RuntimeException("Only accept under 100 rows");
        } else
            throw new RuntimeException("Only accept file excel (*.xls / *.xlsx)");
    }

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

    public boolean isUnder100Rows(MultipartFile file) {
        if (isFileExcel(file)) {
            try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
                // Chọn sheet đầu tiên
                Sheet sheet = workbook.getSheetAt(0);
                int rowCount = sheet.getPhysicalNumberOfRows();
                // Kiểm tra số dòng
                return rowCount <= 100;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private Timestamp numericToTimestamp(double numeric) {
        Date birthdateDate = DateUtil.getJavaDate(numeric);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return Timestamp.valueOf(formatter.format(birthdateDate));
    }
}
