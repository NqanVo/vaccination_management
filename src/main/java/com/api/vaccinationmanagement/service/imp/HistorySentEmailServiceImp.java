package com.api.vaccinationmanagement.service.imp;

import com.api.vaccinationmanagement.model.EmployeeModel;
import com.api.vaccinationmanagement.model.HistorySentEmailModel;
import com.api.vaccinationmanagement.model.PatientModel;
import com.api.vaccinationmanagement.repository.HistorySentEmailRepo;
import com.api.vaccinationmanagement.service.HistorySentEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HistorySentEmailServiceImp implements HistorySentEmailService {
    @Autowired
    private HistorySentEmailRepo historySentEmailRepo;


    public Optional<HistorySentEmailModel> findByEmployee(EmployeeModel employeeModel) {
        return Optional.empty();
    }

    public Optional<HistorySentEmailModel> findByPatient(PatientModel patientModel) {
        return Optional.empty();
    }

    @Override
    public Optional<HistorySentEmailModel> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public HistorySentEmailModel saveNew(HistorySentEmailModel model) {
        return historySentEmailRepo.save(model);
    }

    @Override
    public HistorySentEmailModel saveUpdate(HistorySentEmailModel model) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {

    }
}
