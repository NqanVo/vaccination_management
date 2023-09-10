package com.api.vaccinationmanagement.service.imp;

import com.api.vaccinationmanagement.config.jwt.JwtService;
import com.api.vaccinationmanagement.dto.InputLoginDto;
import com.api.vaccinationmanagement.dto.OutputLoginDto;
import com.api.vaccinationmanagement.exception.NotFoundException;
import com.api.vaccinationmanagement.model.EmployeeModel;
import com.api.vaccinationmanagement.repository.EmployeeRepo;
import com.api.vaccinationmanagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeServiceImp implements EmployeeService {
    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public OutputLoginDto login(InputLoginDto inputLoginDto) {
        EmployeeModel employeeModel = employeeRepo.findEmployeeModelByEmail(inputLoginDto.getEmail())
                .orElseThrow(() -> new NotFoundException("Not found employee with email: " + inputLoginDto.getEmail()));
        if (passwordEncoder.matches(inputLoginDto.getPassword(), employeeModel.getPassword()))
            return OutputLoginDto
                    .builder()
                    .accessToken(jwtService.generateToken(employeeModel, JwtService.TOKEN_TYPE.ACCESS_TOKEN))
                    .refreshToken(jwtService.generateToken(employeeModel, JwtService.TOKEN_TYPE.REFRESH_TOKEN))
                    .build();
        else
            throw new NotFoundException("Wrong password");
    }

    @Override
    public Optional<EmployeeModel> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public EmployeeModel saveNew(EmployeeModel model) {
        return null;
    }

    @Override
    public EmployeeModel saveUpdate(EmployeeModel model) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {

    }
}
