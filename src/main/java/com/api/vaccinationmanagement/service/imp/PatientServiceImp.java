package com.api.vaccinationmanagement.service.imp;

import com.api.vaccinationmanagement.config.jwt.JwtService;
import com.api.vaccinationmanagement.converter.PatientConverter;
import com.api.vaccinationmanagement.dto.patient.InputPatientDto;
import com.api.vaccinationmanagement.exception.NotFoundException;
import com.api.vaccinationmanagement.exception.UnAuthorizationException;
import com.api.vaccinationmanagement.model.EmployeeModel;
import com.api.vaccinationmanagement.model.HistorySentEmailModel;
import com.api.vaccinationmanagement.model.PatientModel;
import com.api.vaccinationmanagement.repository.EmployeeRepo;
import com.api.vaccinationmanagement.repository.HistorySentEmailRepo;
import com.api.vaccinationmanagement.repository.PatientRepo;
import com.api.vaccinationmanagement.service.PatientService;
import com.api.vaccinationmanagement.service.RoleService;
import jakarta.mail.MessagingException;
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
import java.util.*;

@Service
@Slf4j
public class PatientServiceImp implements PatientService {
    @Autowired
    private PatientRepo patientRepo;
    @Autowired
    EntityManager entityManager;
    @Autowired
    private RoleService roleService;
    @Autowired
    private EmailServiceImp emailService;
    @Autowired
    private HistorySentEmailRepo historySentEmailRepo;
    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private JwtService jwtService;

    @Override
    public Page<PatientModel> findByFilters(String fullname, String email,
                                            String phone, Timestamp birthdateFrom,
                                            Timestamp birthdateTo, Integer ageFrom,
                                            Integer ageTo, String addressCode,
                                            Pageable pageable) {
        String actualAddressCode = roleService.getRoleRegionForGet(addressCode);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PatientModel> filtersQuery = criteriaBuilder.createQuery(PatientModel.class);
        // from patient
        Root<PatientModel> patientModelRoot = filtersQuery.from(PatientModel.class);
        // select * {patientModelRoot}
        filtersQuery.select(patientModelRoot);
        // Xay dung dieu kien
        Predicate filtersPredicate = predicateBuild(criteriaBuilder, patientModelRoot, fullname, email, phone, birthdateFrom, birthdateTo, ageFrom, ageTo, actualAddressCode);
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
    public String sendEmailToPatients(String title, String message, List<Integer> listIdPatient) {
        Set<Integer> sent = new HashSet<>();
        int sentTotal = listIdPatient.size();
        int sentSuccess = 0;
        EmployeeModel employeeModel = employeeRepo.findEmployeeModelByEmail(jwtService.getEmail()).get();
        for (Integer id : listIdPatient) {
            if (sent.contains(id)) continue;
            else {
                sent.add(id);
                Optional<PatientModel> patientModel = patientRepo.findById(id);
                if (patientModel.isEmpty()) continue;
                else {
                    String replaceMessage = message.replace("@name", patientModel.get().getFullname());
                    try {
                        emailService.sendMail(patientModel.get().getEmail(), title, replaceMessage);
                        HistorySentEmailModel historySentEmailModel = HistorySentEmailModel
                                .builder()
                                .employeeModel(employeeModel)
                                .patientModel(patientModel.get())
                                .title(title)
                                .content(replaceMessage)
                                .sentAt(new Timestamp(new Date().getTime()))
                                .build();
                        historySentEmailRepo.save(historySentEmailModel);
                        sentSuccess++;
                    } catch (MessagingException ex) {
                        continue;
                    }
                }
            }
        }
        sent.clear();
        return sentSuccess == 0 ? "Send fail, please recheck" : "Sent success " + sentSuccess + "/" + sentTotal;
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
                                     Timestamp birthdateTo, Integer ageFrom,
                                     Integer ageTo, String addressCode) {
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
        // Lọc theo khoảng tuổi
        else if ((ageFrom != null && ageTo != null) && (ageFrom <= ageTo)) {
            // Tính ngày sinh tối đa từ tuổi tối đa
            Calendar calMax = Calendar.getInstance();
            calMax.setTimeInMillis(System.currentTimeMillis());
            calMax.add(Calendar.YEAR, -ageTo);
            Timestamp birthdateMax = new Timestamp(calMax.getTimeInMillis());
            // Tính ngày sinh tối thiểu từ tuổi tối thiểu
            Calendar calMin = Calendar.getInstance();
            calMin.setTimeInMillis(System.currentTimeMillis());
            calMin.add(Calendar.YEAR, -ageFrom);
            Timestamp birthdateMin = new Timestamp(calMin.getTimeInMillis());

            Predicate predicate = criteriaBuilder.between(patientModelRoot.get("birthdate"), birthdateMax, birthdateMin);
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
