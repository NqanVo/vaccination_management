package com.api.vaccinationmanagement.service.imp;

import com.api.vaccinationmanagement.config.jwt.JwtService;
import com.api.vaccinationmanagement.converter.EmployeeConverter;
import com.api.vaccinationmanagement.dto.InputLoginDto;
import com.api.vaccinationmanagement.dto.InputSignUpDto;
import com.api.vaccinationmanagement.dto.OutputEmployeeDto;
import com.api.vaccinationmanagement.dto.OutputLoginDto;
import com.api.vaccinationmanagement.exception.NotFoundException;
import com.api.vaccinationmanagement.exception.ObjectAlreadyExistsException;
import com.api.vaccinationmanagement.exception.UnAuthorizationException;
import com.api.vaccinationmanagement.model.EmployeeModel;
import com.api.vaccinationmanagement.model.RoleModel;
import com.api.vaccinationmanagement.repository.EmployeeRepo;
import com.api.vaccinationmanagement.service.EmployeeService;
import com.api.vaccinationmanagement.service.RoleService;
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
    @Autowired
    private RoleService roleService;

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
    public OutputEmployeeDto signUp(InputSignUpDto inputSignUpDto) {
        Optional<EmployeeModel> employeeModel = employeeRepo.findEmployeeModelByEmail(inputSignUpDto.getEmail());
        if (employeeModel.isPresent())
            throw new ObjectAlreadyExistsException("Already exists employee with email: " + inputSignUpDto.getEmail());
        RoleModel roleEmployee = roleService.findByCode("ROLE_EMPLOYEE").get();
        EmployeeModel model = EmployeeConverter.InputToModelCreate(inputSignUpDto);
        model.setRoleModel(roleEmployee);
        model.setPassword(passwordEncoder.encode(inputSignUpDto.getPassword()));
        model.setEnabled(true);
        return EmployeeConverter.ModelToOutput(employeeRepo.save(model));
    }

    @Override
    public OutputLoginDto refreshToken(String refreshToken) {
        String tokenType = jwtService.getTokenType(refreshToken);
        if (tokenType != null) {
            if (tokenType.equals("REFRESH_TOKEN") && jwtService.validateToken(refreshToken)) {
                String getEmail = jwtService.getEmailFromJwt(refreshToken);
                Optional<EmployeeModel> employeeModel = employeeRepo.findEmployeeModelByEmail(getEmail);

                if (employeeModel.isPresent()) {
                    String accessToken = jwtService.generateToken(employeeModel.get(), JwtService.TOKEN_TYPE.ACCESS_TOKEN);
                    String refreshTokenNew = jwtService.generateToken(employeeModel.get(), JwtService.TOKEN_TYPE.REFRESH_TOKEN);
                    return OutputLoginDto
                            .builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshTokenNew)
                            .build();
                } else {
                    throw new UnAuthorizationException("Refresh token is not valid");
                }
            } else {
                throw new UnAuthorizationException("Refresh token is not valid");
            }
        } else {
            throw new UnAuthorizationException("Invalid token type");
        }
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
