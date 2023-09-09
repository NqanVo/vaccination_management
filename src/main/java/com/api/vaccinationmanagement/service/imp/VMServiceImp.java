package com.api.vaccinationmanagement.service.imp;

import com.api.vaccinationmanagement.conveter.VMModelConverter;
import com.api.vaccinationmanagement.exception.NotFoundException;
import com.api.vaccinationmanagement.model.*;
import com.api.vaccinationmanagement.repository.PatientRepo;
import com.api.vaccinationmanagement.repository.SickRepo;
import com.api.vaccinationmanagement.repository.VMRepo;
import com.api.vaccinationmanagement.repository.VaccineRepo;
import com.api.vaccinationmanagement.service.VMService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VMServiceImp implements VMService {
    @Autowired
    private VMRepo vmRepo;
    @Autowired
    private PatientRepo patientRepo;
    @Autowired
    private SickRepo sickRepo;
    @Autowired
    private VaccineRepo vaccineRepo;
    @Autowired
    EntityManager entityManager;

    @Override
    public Page<VMModel> findByFilters(Integer patientId, Integer sickId,
                                       Integer vaccineId, Timestamp vaccinationFrom,
                                       Timestamp vaccinationTo, String addressCode, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<VMModel> filtersQuery = criteriaBuilder.createQuery(VMModel.class);
        Root<VMModel> vmModelRoot = filtersQuery.from(VMModel.class);
        filtersQuery.select(vmModelRoot);
        Predicate filtersPredicate = predicateBuild(criteriaBuilder, vmModelRoot, patientId, sickId, vaccineId, vaccinationFrom, vaccinationTo, addressCode);
        filtersQuery.where(filtersPredicate).orderBy(criteriaBuilder.asc(vmModelRoot.get("id")));
        // Thuc hien count
        TypedQuery<VMModel> vmModelTypedCount = entityManager.createQuery(filtersQuery);
        // Thuc hien query
        TypedQuery<VMModel> vmModelTypedQuery = entityManager.createQuery(filtersQuery);
        // Thuc hien phan trang
        vmModelTypedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        vmModelTypedQuery.setMaxResults(pageable.getPageSize());

        List<VMModel> listResults = vmModelTypedQuery.getResultList();
        int totalResults = vmModelTypedCount.getResultList().size();

        return new PageImpl<>(listResults, pageable, totalResults);
    }

    @Override
    public Optional<VMModel> findById(Integer id) {
        Optional<VMModel> vmModel = vmRepo.findById(id);
        if (vmModel.isPresent())
            return vmModel;
        else
            throw new NotFoundException("Not found history vaccination with id: " + id);
    }

    @Override
    public VMModel saveNew(VMModel model) {
        return null;
    }

    @Override
    public VMModel saveUpdate(VMModel model) {
        return null;
    }

    @Override
    public List<HistoryVaccinationModel> findHistoryVaccinationByPatient(Integer id) {
        Optional<PatientModel> patientModel = patientRepo.findById(id);
        if (patientModel.isPresent()) {
            List<HistoryVaccinationModel> list = new ArrayList<>();
            List<VMModel> vmModelList = patientModel.get().getVmModelList();
            vmModelList.forEach(vm -> {
                list.add(VMModelConverter.VMToHistoryModel(vm));
            });
            return list;
        } else
            throw new NotFoundException("Not found patient with id: " + id);
    }

    @Override
    public VMModel saveNew(InputVMModel model) {
        Optional<PatientModel> patientModel = patientRepo.findById(model.getPatientId());
        Optional<SickModel> sickModel = sickRepo.findById(model.getSickId());
        Optional<VaccineModel> vaccineModel = vaccineRepo.findById(model.getVaccineId());
        if (patientModel.isPresent() && sickModel.isPresent() && vaccineModel.isPresent()) {
            VMModel vmModel = VMModel.builder()
                    .patientModel(patientModel.get())
                    .sickModel(sickModel.get())
                    .vaccineModel(vaccineModel.get())
                    .vaccinationDate(model.getVaccinationDate())
                    .build();
            return vmRepo.save(vmModel);
        } else
            throw new NotFoundException("The information entered is not appropriate, recheck!");
    }

    @Override
    public VMModel saveUpdate(InputVMModel model) {
        Optional<VMModel> vmModel = vmRepo.findById(model.getVmId());
        Optional<PatientModel> patientModel = patientRepo.findById(model.getPatientId());
        Optional<SickModel> sickModel = sickRepo.findById(model.getSickId());
        Optional<VaccineModel> vaccineModel = vaccineRepo.findById(model.getVaccineId());
        if (vmModel.isPresent() && patientModel.isPresent() && sickModel.isPresent() && vaccineModel.isPresent()) {
            VMModel vm = VMModel.builder()
                    .id(vmModel.get().getId())
                    .patientModel(patientModel.get())
                    .sickModel(sickModel.get())
                    .vaccineModel(vaccineModel.get())
                    .vaccinationDate(model.getVaccinationDate())
                    .build();
            return vmRepo.save(vm);
        } else
            throw new NotFoundException("The information entered is not appropriate, recheck!");
    }

    @Override
    public void deleteById(Integer id) {
        Optional<VMModel> vmModel = vmRepo.findById(id);
        if (vmModel.isPresent())
            vmRepo.deleteById(id);
        else
            throw new NotFoundException("Not found vaccination management with id: " + id);
    }

    private Predicate predicateBuild(CriteriaBuilder criteriaBuilder, Root<VMModel> vmModelRoot,
                                     Integer patientId, Integer sickId,
                                     Integer vaccineId, Timestamp vaccinationFrom,
                                     Timestamp vaccinationTo, String addressCode) {
        List<Predicate> predicateList = new ArrayList<>();
        // Lọc theo patientId
        if (patientId != null) {
            Predicate predicate = criteriaBuilder.equal(vmModelRoot.get("patientModel").get("id"), patientId);
            predicateList.add(predicate);
        }
        // Lọc theo sickId
        if (sickId != null) {
            Predicate predicate = criteriaBuilder.equal(vmModelRoot.get("sickModel").get("id"), sickId);
            predicateList.add(predicate);
        }
        // Lọc theo sickId
        if (vaccineId != null) {
            Predicate predicate = criteriaBuilder.equal(vmModelRoot.get("vaccineModel").get("id"), vaccineId);
            predicateList.add(predicate);
        }
        // Lọc theo khoan ngay
        if ((vaccinationFrom != null && vaccinationTo != null) && (vaccinationFrom.before(vaccinationTo))) {
            Predicate predicate = criteriaBuilder.between(vmModelRoot.get("vaccinationDate"), vaccinationFrom, vaccinationTo);
            predicateList.add(predicate);
        }
        // Lọc theo addressCode
        if (addressCode != null && !addressCode.isBlank()) {
            Predicate predicate = criteriaBuilder.like(vmModelRoot.get("patientModel").get("addressCode"), addressCode + "%");
            predicateList.add(predicate);
        }
        return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
    }
}
