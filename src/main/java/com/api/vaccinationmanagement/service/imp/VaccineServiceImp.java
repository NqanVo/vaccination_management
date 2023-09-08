package com.api.vaccinationmanagement.service.imp;

import com.api.vaccinationmanagement.model.PatientModel;
import com.api.vaccinationmanagement.model.VaccineModel;
import com.api.vaccinationmanagement.repository.VaccineRepo;
import com.api.vaccinationmanagement.service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VaccineServiceImp implements VaccineService {
    @Autowired
    private VaccineRepo vaccineRepo;
    @Override
    public Optional<VaccineModel> findById(Integer id) {
        Optional<VaccineModel> vaccineModel = vaccineRepo.findById(id);
        if (vaccineModel.isEmpty())
            throw new RuntimeException("Not found");
        return vaccineModel;
    }

    @Override
    public VaccineModel saveNew(VaccineModel model) {
        return vaccineRepo.save(model);
    }

    @Override
    public VaccineModel saveUpdate(VaccineModel model) {
        return vaccineRepo.save(model);
    }

    @Override
    public void deleteById(Integer id) {
        Optional<VaccineModel> vaccineModel = vaccineRepo.findById(id);
        if (vaccineModel.isEmpty())
            throw new RuntimeException("Not found");
        vaccineRepo.deleteById(id);
    }
}
