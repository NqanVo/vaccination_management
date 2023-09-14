package com.api.vaccinationmanagement.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ExcelService {
    String processExcelFile(MultipartFile file) throws IOException;

    boolean isFileExcel(MultipartFile file);

    boolean isUnder100Rows(MultipartFile file);
}
