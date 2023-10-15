package com.api.vaccinationmanagement.config.jwt;

import com.api.vaccinationmanagement.exception.NotFoundException;
import com.api.vaccinationmanagement.model.EmployeeModel;
import com.api.vaccinationmanagement.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private EmployeeRepo employeeRepo;

    @Override
    public EmployeeModel loadUserByUsername(String username) throws UsernameNotFoundException {
        return employeeRepo.findEmployeeModelByEmail(username)
                .orElseThrow(() -> new NotFoundException("Not found employee with email: " + username));
    }
}
