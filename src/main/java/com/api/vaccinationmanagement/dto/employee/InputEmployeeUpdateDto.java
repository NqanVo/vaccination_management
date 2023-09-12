package com.api.vaccinationmanagement.dto.employee;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InputEmployeeUpdateDto {
    @NotNull(message = "Not null")
    @NotBlank(message = "Not blank")
    @Size(min = 5, max = 50, message = "Min 5 character, max 50 character")
    private String fullname;

    @NotNull(message = "Not null")
    @NotBlank(message = "Not blank")
    @Pattern(regexp = "^(\\d{4}|\\d{4}-\\d{4}|\\d{4}-\\d{4}-\\d{4})$", message = "Invalid roleRegion format")
    private String roleRegion;

    @NotNull(message = "Not null")
    @Digits(integer = 10, fraction = 0, message = "Invalid format")
    private Integer roleId;

    @Pattern(regexp = "true|false|1|0", message = "Invalid value for enabled, must be true, false, 1, or 0")
    private boolean enabled;
}
