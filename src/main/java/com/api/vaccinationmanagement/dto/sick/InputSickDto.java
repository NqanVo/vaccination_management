package com.api.vaccinationmanagement.dto.sick;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InputSickDto {
    private Integer id;

    @NotNull(message = "Not null")
    @NotBlank(message = "Not blank")
    @Size(min = 5, max = 50, message = "Min 5 character, max 50 character")
    private String name;

    @NotNull(message = "Not null")
    @NotBlank(message = "Not blank")
    @Size(min = 5, message = "Min 5 character")
    private String description;
}
