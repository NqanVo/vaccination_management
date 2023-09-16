package com.api.vaccinationmanagement.dto.patient;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputSentEmailPatientDto {
    @NotNull(message = "Not null")
    @NotBlank(message = "Not blank")
    @Size(min = 3, max = 100, message = "Min 3 character, max 100 character")
    private String title;
    @NotNull(message = "Not null")
    @NotBlank(message = "Not blank")
    private String message;
    @NotNull(message = "Not null")
    private List<Integer> listPatientId = new ArrayList<>();
}
