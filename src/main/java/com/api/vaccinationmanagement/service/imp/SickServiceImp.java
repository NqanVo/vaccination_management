package com.api.vaccinationmanagement.service.imp;

import com.api.vaccinationmanagement.model.SickModel;
import com.api.vaccinationmanagement.repository.SickRepo;
import com.api.vaccinationmanagement.service.SickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SickServiceImp implements SickService {
    @Autowired
    private SickRepo sickRepo;

    @Override
    public Optional<SickModel> findById(Integer id) {
        Optional<SickModel> sickModel = sickRepo.findById(id);
        if(sickModel.isEmpty()) throw new RuntimeException("Not found");
        return sickModel;
    }

    @Override
    public SickModel saveNew(SickModel model) {
        return sickRepo.save(model);
    }

    @Override
    public SickModel saveUpdate(SickModel model) {
        return sickRepo.save(model);
    }

    @Override
    public void deleteById(Integer id) {
        Optional<SickModel> sickModel = sickRepo.findById(id);
        if(sickModel.isEmpty()) throw new RuntimeException("Not found");
        sickRepo.deleteById(id);
    }
}
