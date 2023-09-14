package com.api.vaccinationmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "employee")
@Entity(name = "employee")
public class EmployeeModel extends BaseModel implements UserDetails {
    @Column(length = 50)
    private String fullname;
    @Column(length = 50, unique = true)
    private String email;
    @Column(length = 255)
    private String password;
    @Column(length = 15)
    private String roleRegion;
    private boolean enabled;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleModel roleModel;
    @JsonIgnore
    @OneToMany(mappedBy = "employeeModel",cascade = CascadeType.ALL)
    private List<HistorySentEmailModel> historySentEmailModels = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        grantedAuthorityList.add(roleModel);
        return grantedAuthorityList;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}
