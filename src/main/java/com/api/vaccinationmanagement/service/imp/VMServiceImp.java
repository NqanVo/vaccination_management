package com.api.vaccinationmanagement.service.imp;

import com.api.vaccinationmanagement.converter.VMModelConverter;
import com.api.vaccinationmanagement.dto.patient.HistoryVaccinationDto;
import com.api.vaccinationmanagement.dto.InputVMDto;
import com.api.vaccinationmanagement.dto.statistical.RateVaccinatedBySIckIdAndRegionDto;
import com.api.vaccinationmanagement.dto.statistical.RateVaccinatedEachSickDto;
import com.api.vaccinationmanagement.dto.statistical.StatisticalRateVaccinatedBySickIdAndRegionDto;
import com.api.vaccinationmanagement.dto.statistical.StatisticalRateVaccinatedEachSick;
import com.api.vaccinationmanagement.exception.NotFoundException;
import com.api.vaccinationmanagement.model.*;
import com.api.vaccinationmanagement.repository.PatientRepo;
import com.api.vaccinationmanagement.repository.SickRepo;
import com.api.vaccinationmanagement.repository.VMRepo;
import com.api.vaccinationmanagement.repository.VaccineRepo;
import com.api.vaccinationmanagement.service.RoleService;
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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

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
    private EntityManager entityManager;
    @Autowired
    private RoleService roleService;

    @Override
    public Page<PatientModel> findPatientVaccinatedWithSickIdAndMinDosesAndAddressCode(
            Integer sickId, Integer minDoses, String addressCode, Pageable pageable) {
        String actualAddressCode = roleService.getRoleRegionForGet(addressCode);
        return vmRepo.findPatientVaccinatedWithSickIdAndMinDosesAndAddressCode(sickId, minDoses, actualAddressCode, pageable);
    }

    @Override
    public StatisticalRateVaccinatedEachSick findRatePatientVaccinatedEachSick() {
        List<Object[]> dataRaw = vmRepo.findRatePatientVaccinatedEachSick();

        StatisticalRateVaccinatedEachSick statisticalRateVaccinatedEachSick = new StatisticalRateVaccinatedEachSick();
        dataRaw.forEach(data -> {
            BigDecimal bigDecimal = (BigDecimal) data[0];
            Double doubleValue = bigDecimal.doubleValue(); // Chuyển đổi thành Double

            RateVaccinatedEachSickDto rateVaccinatedEachSickDto = RateVaccinatedEachSickDto.builder()
                    .rate(doubleValue)
                    .nameSick((String) data[1])
                    .build();
            statisticalRateVaccinatedEachSick.getRateVaccinatedEachSickDtoList().add(rateVaccinatedEachSickDto);
        });

        statisticalRateVaccinatedEachSick.setTotalPatient(patientRepo.findAll().size());

        return statisticalRateVaccinatedEachSick;
    }

    @Override
    public StatisticalRateVaccinatedBySickIdAndRegionDto findRatePatientVaccinatedBySickIdAndRegion(
            Integer sickId, String addressCode) {
        List<Object[]> dataRaw;
        if (addressCode.equals("0000"))
            dataRaw = vmRepo.findRatePatientVaccinatedBySickIdAndAllCity(sickId);
        else if (addressCode.length() == 4)
            dataRaw = vmRepo.findRatePatientVaccinatedBySickIdAndCity(sickId, addressCode);
        else if(addressCode.length() == 9)
            dataRaw = vmRepo.findRatePatientVaccinatedBySickIdAndDistrict(sickId, addressCode);
        else
            dataRaw = vmRepo.findRatePatientVaccinatedBySickIdAndCommune(sickId, addressCode);

        if (dataRaw.size() == 0) throw new NotFoundException("Not found data with this region");

        List<RateVaccinatedBySIckIdAndRegionDto> rateRegion = new ArrayList<>();
        dataRaw.forEach(data -> {
            Long injected = (Long) data[3];
            Long total = (Long) data[4];
            int injectedValue = injected.intValue();
            int totalValue = total.intValue();

            RateVaccinatedBySIckIdAndRegionDto rate = RateVaccinatedBySIckIdAndRegionDto
                    .builder()
                    .addressCode((String) data[2])
                    .injected(injectedValue)
                    .total(totalValue)
                    .rate((double) injectedValue / totalValue)
                    .build();

            rateRegion.add(rate);
        });

        return StatisticalRateVaccinatedBySickIdAndRegionDto
                .builder()
                .sickId((Integer) dataRaw.get(0)[0])
                .sickName((String) dataRaw.get(0)[1])
                .region(rateRegion)
                .build();
    }

    @Override
    public Page<VMModel> findByFilters(Integer patientId, Integer sickId,
                                       Integer vaccineId, Timestamp vaccinationFrom,
                                       Timestamp vaccinationTo, String addressCode, Pageable pageable) {
        String actualAddressCode = roleService.getRoleRegionForGet(addressCode);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<VMModel> filtersQuery = criteriaBuilder.createQuery(VMModel.class);
        Root<VMModel> vmModelRoot = filtersQuery.from(VMModel.class);
        filtersQuery.select(vmModelRoot);
        Predicate filtersPredicate = predicateBuild(criteriaBuilder, vmModelRoot, patientId, sickId, vaccineId, vaccinationFrom, vaccinationTo, actualAddressCode);
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
        if (vmModel.isPresent()) {
            roleService.getRoleRegionForGet(vmModel.get().getPatientModel().getAddressCode());
            return vmModel;
        } else
            throw new NotFoundException("Not found history vaccination with id: " + id);
    }

    @Override
    public List<HistoryVaccinationDto> findHistoryVaccinationByPatient(Integer id) {
        String addressCode = roleService.getRoleRegionFromJwt();
        Optional<PatientModel> patientModel = patientRepo.findPatientModelByIdAndAddressCodeLike(id, addressCode);
        if (patientModel.isPresent()) {
            List<HistoryVaccinationDto> list = new ArrayList<>();
            List<VMModel> vmModelList = patientModel.get().getVmModelList();
            vmModelList.forEach(vm -> {
                list.add(VMModelConverter.VMToHistoryModel(vm));
            });
            return list;
        } else
            throw new NotFoundException("Not found patient with id: " + id + " or patient outside the area you manage");
    }

    @Override
    public VMModel saveNew(InputVMDto dto) {
        PatientModel patientModel = patientRepo.findById(dto.getPatientId())
                .orElseThrow(() -> new NotFoundException("Not found patient with id: " + dto.getPatientId()));
        roleService.getRoleRegionForSet(patientModel.getAddressCode());
        SickModel sickModel = sickRepo.findById(dto.getSickId())
                .orElseThrow(() -> new NotFoundException("Not found sick with id: " + dto.getSickId()));
        VaccineModel vaccineModel = vaccineRepo.findById(dto.getVaccineId())
                .orElseThrow(() -> new NotFoundException("Not found vaccine with id: " + dto.getVaccineId()));
        boolean vaccineMatchesSick = sickModel.getVaccineModelList().contains(vaccineModel);
        if (!vaccineMatchesSick) throw new RuntimeException("Vaccine not matches sick");
        VMModel vmModel = VMModel.builder()
                .patientModel(patientModel)
                .sickModel(sickModel)
                .vaccineModel(vaccineModel)
                .vaccinationDate(dto.getVaccinationDate())
                .build();
        return vmRepo.save(vmModel);
    }

    @Override
    public VMModel saveUpdate(InputVMDto dto) {
        VMModel vmModelOld = vmRepo.findById(dto.getVmId())
                .orElseThrow(() -> new NotFoundException("Not found vaccination management with id: " + dto.getVmId()));
        roleService.getRoleRegionForSet(vmModelOld.getPatientModel().getAddressCode());
        if (!Objects.equals(dto.getPatientId(), vmModelOld.getPatientModel().getId())) {
            PatientModel patientModel = patientRepo.findById(dto.getPatientId())
                    .orElseThrow(() -> new NotFoundException("Not found patient with id: " + dto.getPatientId()));
            roleService.getRoleRegionForSet(patientModel.getAddressCode());
            vmModelOld.setPatientModel(patientModel);
        }
        if (!Objects.equals(dto.getSickId(), vmModelOld.getSickModel().getId())) {
            SickModel sickModel = sickRepo.findById(dto.getSickId())
                    .orElseThrow(() -> new NotFoundException("Not found sick with id: " + dto.getSickId()));
            vmModelOld.setSickModel(sickModel);
        }
        if (!Objects.equals(dto.getVaccineId(), vmModelOld.getVaccineModel().getId())) {
            VaccineModel vaccineModel = vaccineRepo.findById(dto.getVaccineId())
                    .orElseThrow(() -> new NotFoundException("Not found vaccine with id: " + dto.getVaccineId()));
            vmModelOld.setVaccineModel(vaccineModel);
        }
        boolean vaccineMatchesSick = vmModelOld.getSickModel().getVaccineModelList().contains(vmModelOld.getVaccineModel());
        if (!vaccineMatchesSick) throw new RuntimeException("Vaccine not matches sick");
        vmModelOld.setVaccinationDate(dto.getVaccinationDate());
        return vmRepo.save(vmModelOld);
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
