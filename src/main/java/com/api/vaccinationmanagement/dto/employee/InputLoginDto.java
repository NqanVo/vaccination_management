package com.api.vaccinationmanagement.dto.employee;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InputLoginDto {
    @NotNull(message = "Not null")
    @NotBlank(message = "Not blank")
    private String email;
    @NotNull(message = "Not null")
    @NotBlank(message = "Not blank")
    @Size(min = 3, max = 16, message = "Password length must be between 3 and 16 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Password must contain at least one lowercase letter, one uppercase letter, and one digit")
    private String password;
}
