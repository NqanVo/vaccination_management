package com.api.vaccinationmanagement.dto.employee;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InputChangePasswordDto {
    @NotNull(message = "Not null")
    @NotBlank(message = "Not blank")
    @Size(min = 3, max = 16, message = "Password length must be between 3 and 16 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Password must contain at least one lowercase letter, one uppercase letter, and one digit")
    private String oldPassword;
    @NotNull(message = "Not null")
    @NotBlank(message = "Not blank")
    @Size(min = 3, max = 16, message = "Password length must be between 3 and 16 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Password must contain at least one lowercase letter, one uppercase letter, and one digit")
    private String newPassword;
}
