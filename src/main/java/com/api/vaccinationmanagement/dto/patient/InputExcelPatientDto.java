package com.api.vaccinationmanagement.dto.patient;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InputExcelPatientDto {

//    @NotNull(message = "Not null")
//    @NotBlank(message = "Not blank")
//    @Size(min = 5, max = 50, message = "Min 5 character, max 50 character")
//    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "Only letters and spaces are allowed")
//    private String fullname;
//
//    @NotNull(message = "Not null")
//    @NotBlank(message = "Not blank")
//    @Size(min = 5, max = 50, message = "Min 5 character, max 50 character")
//    private String email;
//
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
//    @NotNull(message = "Not null")
//    @Past(message = "Birthdate must be in the past")
//    private Timestamp birthdate;
//
//    @NotNull(message = "Not null")
//    @NotBlank(message = "Not blank")
//    @Pattern(regexp = "^\\d{3}-\\d{3}-\\d{4}$", message = "Invalid phone format. Should be in the format xxx-xxx-xxxx")
//    private String phone;
//
//    @NotNull(message = "Not null")
//    @NotBlank(message = "Not blank")
//    @Pattern(regexp = "^\\d{4}-\\d{4}-\\d{4}$", message = "Invalid format. Should be in the format xxxx-xxxx-xxxx")
//    private String addressCode;


    private MultipartFile file;
}
