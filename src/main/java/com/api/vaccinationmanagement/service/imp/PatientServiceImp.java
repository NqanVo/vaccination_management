package com.api.vaccinationmanagement.service.imp;

import com.api.vaccinationmanagement.exception.NotFoundException;
import com.api.vaccinationmanagement.model.PatientModel;
import com.api.vaccinationmanagement.repository.PatientRepo;
import com.api.vaccinationmanagement.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImp implements PatientService {
    @Autowired
    private PatientRepo patientRepo;

    @Override
    public Page<PatientModel> findByRoleRegion(Pageable pageable) {
        String region = "0001%";
        return patientRepo.findPatientModelsByAddressCodeLike(pageable, region);
    }

    @Override
    public Optional<PatientModel> findById(Integer id) {
        Optional<PatientModel> patient = patientRepo.findById(id);
        if (patient.isPresent())
            return patient;
        else
            throw new NotFoundException("Not found patient with id: " + id);

    }

    @Override
    public List<PatientModel> findByEmail(String email) {
        return patientRepo.findPatientModelsByEmail(email);
    }

    @Override
    public PatientModel saveNew(PatientModel model) {
        return patientRepo.save(model);
    }

    @Override
    public PatientModel saveUpdate(PatientModel model) {
        Optional<PatientModel> patient = patientRepo.findById(model.getId());
        if (patient.isPresent())
            // Lấy email và role từ jwt để kiểm tra.
            // Nếu có quyền sẽ update, không thì lỗi 403
            return null;
        else
            throw new NotFoundException("Not found patient with id: " + model.getId());
    }

    @Override
    public void deleteById(Integer id) {
        Optional<PatientModel> patient = patientRepo.findById(id);
        if (patient.isPresent())
            patientRepo.deleteById(id);
        else
            throw new NotFoundException("Not found patient with id: " + id);
    }
}
