package com.api.vaccinationmanagement.service.imp;

import com.api.vaccinationmanagement.config.jwt.JwtService;
import com.api.vaccinationmanagement.converter.EmployeeConverter;
import com.api.vaccinationmanagement.dto.employee.*;
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
            throw new RuntimeException("Wrong password");
    }

    @Override
    public OutputEmployeeDto signUp(InputSignUpDto inputSignUpDto) {
        Optional<EmployeeModel> employeeModel = employeeRepo.findEmployeeModelByEmail(inputSignUpDto.getEmail());
        if (employeeModel.isPresent())
            throw new ObjectAlreadyExistsException("Already exists employee with email: " + inputSignUpDto.getEmail());
        RoleModel roleEmployee = roleService.findByCode("ROLE_EMPLOYEE").get();
        EmployeeModel model = EmployeeConverter.InputToModelCreate(inputSignUpDto);
        model.setRoleModel(roleEmployee);
        model.setEnabled(true);
        model.setPassword(passwordEncoder.encode(inputSignUpDto.getPassword()));
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
    public Optional<OutputEmployeeDto> findById(Integer id) {
        Optional<EmployeeModel> employeeModel = employeeRepo.findById(id);
        if (employeeModel.isPresent())
            return Optional.of(EmployeeConverter.ModelToOutput(employeeModel.get()));
        else
            throw new NotFoundException("Not found employee with id: " + id);
    }

    @Override
    public Optional<OutputEmployeeDto> findByEmail(String email) {
        Optional<EmployeeModel> employeeModel = employeeRepo.findEmployeeModelByEmail(email);
        if (employeeModel.isPresent())
            return Optional.of(EmployeeConverter.ModelToOutput(employeeModel.get()));
        else
            throw new NotFoundException("Not found employee with email: " + email);
    }

    @Override
    public OutputEmployeeDto saveUpdate(InputEmployeeUpdateDto dto, String email) {
        Optional<EmployeeModel> employeeModel = employeeRepo.findEmployeeModelByEmail(email);
        if (employeeModel.isPresent()) {
            // Neu cap nhat role ma khong phai la Admin hoac cap nhat role region ma khong phai la Admin/Manager -> UnAuthorizationException
            boolean changeRole = !dto.getRoleId().equals(employeeModel.get().getRoleModel().getId()) && !roleService.isAdmin();
            boolean changeRoleRegion = !dto.getRoleRegion().equals(employeeModel.get().getRoleRegion()) && !(roleService.isAdmin() || roleService.isManager());
            if (changeRole || changeRoleRegion)
                throw new UnAuthorizationException("UnAuthorization edit role");
            else {
                RoleModel roleModel = null;
                if (!employeeModel.get().getRoleModel().getId().equals(dto.getRoleId())) {
                    roleModel = roleService.findById(dto.getRoleId()).orElseThrow(() -> new NotFoundException("Not found role with id: " + dto.getRoleId()));
                    employeeModel.get().setRoleModel(roleModel);

                }
                EmployeeConverter.InputToModelUpdate(dto, employeeModel.get());
                if (roleModel != null && (roleModel.getCode().equals("ROLE_ADMIN") || roleModel.getCode().equals("ROLE_MANAGER")))
                    employeeModel.get().setRoleRegion("0000");
                return EmployeeConverter.ModelToOutput(employeeRepo.save(employeeModel.get()));
            }
        } else
            throw new NotFoundException("Not found employee with email: " + email);
    }

    @Override
    public OutputEmployeeDto saveUpdatePassword(InputChangePasswordDto dto, String email) {
        Optional<EmployeeModel> employeeModel = employeeRepo.findEmployeeModelByEmail(email);
        if (employeeModel.isPresent()) {
            if (passwordEncoder.matches(dto.getOldPassword(), employeeModel.get().getPassword())) {
                employeeModel.get().setPassword(passwordEncoder.encode(dto.getNewPassword()));
                return EmployeeConverter.ModelToOutput(employeeRepo.save(employeeModel.get()));
            } else
                throw new RuntimeException("Old password not matches");
        } else
            throw new NotFoundException("Not found employee with email: " + email);
    }

    @Override
    public void deleteByEmail(String email) {
        Optional<EmployeeModel> employeeModel = employeeRepo.findEmployeeModelByEmail(email);
        if (employeeModel.isPresent()) {
            employeeRepo.deleteById(employeeModel.get().getId());
        } else
            throw new NotFoundException("Not found employee with email: " + email);
    }
}
