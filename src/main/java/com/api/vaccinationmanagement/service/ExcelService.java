package com.api.vaccinationmanagement.service;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

public interface ExcelService {
    String processExcelFile(MultipartFile file) throws IOException;

    boolean isFileExcel(MultipartFile file);
}
