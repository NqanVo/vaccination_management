package com.api.vaccinationmanagement.dto.employee;

import com.api.vaccinationmanagement.model.RoleModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutputEmployeeDto {
    private Integer id;
    private String fullname;
    private String email;
    private String roleRegion;
    private boolean enabled;
    private RoleModel roleModel;
}
