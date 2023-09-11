package com.api.vaccinationmanagement.service.imp;

import com.api.vaccinationmanagement.config.jwt.JwtService;
import com.api.vaccinationmanagement.exception.UnAuthorizationException;
import com.api.vaccinationmanagement.model.RoleModel;
import com.api.vaccinationmanagement.repository.RoleRepo;
import com.api.vaccinationmanagement.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class RoleServiceImp implements RoleService {
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private JwtService jwtService;

    @Override
    public Optional<RoleModel> findById(Integer id) {
        return roleRepo.findById(id);
    }

    @Override
    public Optional<RoleModel> findByCode(String code) {
        return roleRepo.findRoleModelByCode(code);
    }

    /**
     * Lay role region theo jwt, neu la 0000 duoc truy cap tat ca
     * @return addressCode
     */
    @Override
    public String getRoleRegionFromJwt() {
        return Objects.equals(jwtService.getRoleRegion(), "0000") ? "%" : jwtService.getRoleRegion() + "%";
    }

    /**
     * Lay role region thuc te khi truyen vao addressCodeRequest
     * Neu role region la 0000 thi truy cap theo 0000
     * Neu pham vi truy cap role region thap hon addressCodeRequest -> UnAuthorizationException
     * Kiem tra tung vung cua role region so voi addressCodeRequest ? next : UnAuthorizationException
     * Tra ve actualAddressCode theo jwt
     *
     * @param addressCodeRequest
     * @return actualAddressCode
     * @throws UnAuthorizationException
     */
    @Override
    public String getRoleRegionForSet(String addressCodeRequest) throws UnAuthorizationException {
        if (addressCodeRequest == null)
            throw new UnAuthorizationException("The area the object is accessing is not within your management area");
        else {
            String[] regionJwt = jwtService.getRoleRegion().split("-");
            String[] regionRequest = addressCodeRequest.split("-");
            StringBuilder actualAddressCode = new StringBuilder();
            if (regionJwt[0].equals("0000")) return "%";
            if (regionJwt.length > regionRequest.length)
                throw new UnAuthorizationException("The area the object is accessing is not within your management area");
            for (int i = 0; i < regionJwt.length; i++) {
                if (!regionJwt[i].equals(regionRequest[i]))
                    throw new UnAuthorizationException("The area the object is accessing is not within your management area");
                else
                    actualAddressCode.append(regionJwt[i] + "-");
            }
            return actualAddressCode + "%";
        }
    }

    /**
     * Lay role region thuc te khi truyen vao addressCodeRequest
     * Neu role region la 0000 thi truy cap theo addressCodeRequest
     * Neu pham vi truy cap role region thap hon addressCodeRequest -> UnAuthorizationException
     * Kiem tra tung vung cua role region so voi addressCodeRequest ? next : UnAuthorizationException
     * Tra ve actualAddressCode theo addressCodeRequest
     *
     * @param addressCodeRequest
     * @return actualAddressCode
     * @throws UnAuthorizationException
     */
    @Override
    public String getRoleRegionForGet(String addressCodeRequest) {
        if (addressCodeRequest == null)
            return this.getRoleRegionFromJwt();
        else {
            String[] regionJwt = jwtService.getRoleRegion().split("-");
            String[] regionRequest = addressCodeRequest.split("-");
            if (regionJwt[0].equals("0000")) return addressCodeRequest + "%";
            if (regionJwt.length > regionRequest.length)
                throw new UnAuthorizationException("The object being accessed does not belong to your management area");
            for (int i = 0; i < regionJwt.length; i++) {
                if (!regionJwt[i].equals(regionRequest[i]))
                    throw new UnAuthorizationException("The object being accessed does not belong to your management area");
            }
            return addressCodeRequest + "%";
        }
    }

    @Override
    public String getRole() {
        return jwtService.getRole();
    }

    @Override
    public boolean isAdmin() {
        return getRole().equals("ROLE_ADMIN");
    }

    @Override
    public boolean isManager() {
        return getRole().equals("ROLE_MANAGER");
    }

    @Override
    public boolean isEmployee() {
        return getRole().equals("ROLE_EMPLOYEE");
    }


}
