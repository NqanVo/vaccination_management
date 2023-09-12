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
public class InputSignUpDto {
    @NotNull(message = "Not null")
    @NotBlank(message = "Not blank")
    @Size(min = 5, max = 50, message = "Min 5 character, max 50 character")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "Only letters and spaces are allowed")
    private String fullname;

    @NotNull(message = "Not null")
    @NotBlank(message = "Not blank")
    private String email;

    @NotNull(message = "Not null")
    @NotBlank(message = "Not blank")
    @Size(min = 3, max = 16, message = "Password length must be between 3 and 16 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Password must contain at least one lowercase letter, one uppercase letter, and one digit")
    private String password;

    @NotNull(message = "Not null")
    @NotBlank(message = "Not blank")
    @Pattern(regexp = "^(\\d{4}|\\d{4}-\\d{4}|\\d{4}-\\d{4}-\\d{4})$", message = "Invalid roleRegion format")
    private String roleRegion;
}
