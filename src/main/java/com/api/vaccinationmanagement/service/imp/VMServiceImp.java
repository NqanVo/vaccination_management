package com.api.vaccinationmanagement.service.imp;

import com.api.vaccinationmanagement.model.VMModel;
import com.api.vaccinationmanagement.repository.VMRepo;
import com.api.vaccinationmanagement.service.VMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VMServiceImp implements VMService {
    @Autowired
    private VMRepo vmRepo;
    @Override
    public Optional<VMModel> findById(Integer id) {
        return vmRepo.findById(id);
    }
    @Override
    public VMModel saveNew(VMModel model) {
        return vmRepo.save(model);
    }
    @Override
    public VMModel saveUpdate(VMModel model) {
        return null;
    }
    @Override
    public void deleteById(Integer id) {
        vmRepo.deleteById(id);
    }
}
