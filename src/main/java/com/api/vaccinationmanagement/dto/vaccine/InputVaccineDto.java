package com.api.vaccinationmanagement.dto.vaccine;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InputVaccineDto {
    private Integer id;

    @NotNull(message = "Not null")
    @NotBlank(message = "Not blank")
    @Size(min = 5, max = 50, message = "Min 5 character, max 50 character")
    private String name;
    @NotNull(message = "Not null")
    @NotBlank(message = "Not blank")
    @Size(min = 5, message = "Min 5 character")
    private String description;
    @NotNull(message = "Not null")
    @NotBlank(message = "Not blank")
    private String madeIn;
    @NotNull(message = "Not null")
    @NotBlank(message = "Not blank")
    @Min(value = 0, message = "Value must be at least 0")
    private Integer minAge;
    @Max(value = 99, message = "Value must be at most 99")
    private Integer maxAge;
    @NotNull(message = "Not null")
    @Digits(integer = 10, fraction = 0, message = "Invalid format")
    private Integer sickId;
}
