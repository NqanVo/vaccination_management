package com.api.vaccinationmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "role")
@Table(name = "role")
public class RoleModel extends BaseModel implements GrantedAuthority {
    @Column(length = 50)
    private String name;
    @Column(length = 20)
    private String code;
    @JsonIgnore
    @OneToMany(mappedBy = "roleModel",cascade = CascadeType.ALL)
    private List<EmployeeModel> employeeModelList = new ArrayList<>();

    @Override
    public String getAuthority() {
        return this.code;
    }
}
