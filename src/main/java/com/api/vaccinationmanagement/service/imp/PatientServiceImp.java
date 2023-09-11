package com.api.vaccinationmanagement.service.imp;

import com.api.vaccinationmanagement.config.jwt.JwtService;
import com.api.vaccinationmanagement.converter.PatientConverter;
import com.api.vaccinationmanagement.dto.InputPatientDto;
import com.api.vaccinationmanagement.exception.NotFoundException;
import com.api.vaccinationmanagement.exception.UnAuthorizationException;
import com.api.vaccinationmanagement.model.PatientModel;
import com.api.vaccinationmanagement.repository.PatientRepo;
import com.api.vaccinationmanagement.service.PatientService;
import com.api.vaccinationmanagement.service.RoleService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class PatientServiceImp implements PatientService {
    @Autowired
    private PatientRepo patientRepo;
    @Autowired
    EntityManager entityManager;
    @Autowired
    private RoleService roleService;

    @Override
    public Page<PatientModel> findByFilters(String fullname, String email,
                                            String phone, Timestamp birthdateFrom,
                                            Timestamp birthdateTo, String addressCode,
                                            Pageable pageable) {
        String actualAddressCode = roleService.getRoleRegionForGet(addressCode);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PatientModel> filtersQuery = criteriaBuilder.createQuery(PatientModel.class);
        // from patient
        Root<PatientModel> patientModelRoot = filtersQuery.from(PatientModel.class);
        // select * {patientModelRoot}
        filtersQuery.select(patientModelRoot);
        // Xay dung dieu kien
        Predicate filtersPredicate = predicateBuild(criteriaBuilder, patientModelRoot, fullname, email, phone, birthdateFrom, birthdateTo, actualAddressCode);
        // select * {patientModelRoot} where {filtersPredicate} orderBy {...}
        filtersQuery.where(filtersPredicate).orderBy(criteriaBuilder.asc(patientModelRoot.get("fullname")));
        // Thuc hien count
        TypedQuery<PatientModel> patientModelTypedCount = entityManager.createQuery(filtersQuery);
        // Thuc hien query
        TypedQuery<PatientModel> patientModelTypedQuery = entityManager.createQuery(filtersQuery);
        // Thuc hien phan trang
        patientModelTypedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        patientModelTypedQuery.setMaxResults(pageable.getPageSize());

        List<PatientModel> listResults = patientModelTypedQuery.getResultList();
        int totalResults = patientModelTypedCount.getResultList().size();

        return new PageImpl<>(listResults, pageable, totalResults);
    }

    @Override
    public Optional<PatientModel> findById(Integer id) {
        String addressCode = roleService.getRoleRegionFromJwt();
        Optional<PatientModel> patient = patientRepo.findPatientModelByIdAndAddressCodeLike(id, addressCode);
        if (patient.isPresent())
            return patient;
        else
            throw new NotFoundException("Not found patient with id: " + id + " or patient outside the area you manage");
    }

    @Override
    public PatientModel saveNew(InputPatientDto dto) {
        roleService.getRoleRegionForSet(dto.getAddressCode());
        return patientRepo.save(PatientConverter.InputToModelCreate(dto));
    }

    @Override
    public PatientModel saveUpdate(InputPatientDto dto) throws UnAuthorizationException {
        String addressCode = roleService.getRoleRegionForSet(dto.getAddressCode());
        Optional<PatientModel> patient = patientRepo.findPatientModelByIdAndAddressCodeLike(dto.getId(), addressCode);
        if (patient.isPresent())
            return patientRepo.save(PatientConverter.InputToModelUpdate(dto, patient.get()));
        else
            throw new NotFoundException("Not found patient with id: " + dto.getId() + " or patient outside the area you manage");
    }

    @Override
    public void deleteById(Integer id) {
        Optional<PatientModel> patient = patientRepo.findById(id);
        if (patient.isPresent())
            patientRepo.deleteById(id);
        else
            throw new NotFoundException("Not found patient with id: " + id);
    }

    private Predicate predicateBuild(CriteriaBuilder criteriaBuilder, Root<PatientModel> patientModelRoot,
                                     String fullname, String email,
                                     String phone, Timestamp birthdateFrom,
                                     Timestamp birthdateTo, String addressCode) {
        List<Predicate> predicateList = new ArrayList<>();
        // Lọc theo name
        if (fullname != null && !fullname.isBlank()) {
            Predicate predicate = criteriaBuilder.like(patientModelRoot.get("fullname"), "%" + fullname + "%");
            predicateList.add(predicate);
        }
        // Lọc theo email
        if (email != null && !email.isBlank()) {
            Predicate predicate = criteriaBuilder.equal(patientModelRoot.get("email"), email);
            predicateList.add(predicate);
        }
        // Lọc theo phone
        if (phone != null && !phone.isBlank()) {
            Predicate predicate = criteriaBuilder.equal(patientModelRoot.get("phone"), phone);
            predicateList.add(predicate);
        }
        // Lọc theo addressCode
        if (addressCode != null && !addressCode.isBlank()) {
            Predicate predicate = criteriaBuilder.like(patientModelRoot.get("addressCode"), addressCode + "%");
            predicateList.add(predicate);
        }
        // Lọc theo khoảng ngày
        if ((birthdateFrom != null && birthdateTo != null) && (birthdateFrom.before(birthdateTo))) {
            Predicate predicate = criteriaBuilder.between(patientModelRoot.get("birthdate"), birthdateFrom, birthdateTo);
            predicateList.add(predicate);
        }
        // Lọc theo ngày chính xác (not work)
        if ((birthdateFrom != null && birthdateTo != null) && (birthdateFrom.equals(birthdateTo))) {
            Predicate predicate = criteriaBuilder.equal(patientModelRoot.get("birthdate"), birthdateFrom);
            predicateList.add(predicate);
        }
        return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
    }
}
