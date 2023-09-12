package com.api.vaccinationmanagement.service.imp;

import com.api.vaccinationmanagement.converter.VaccineConverter;
import com.api.vaccinationmanagement.dto.vaccine.InputVaccineDto;
import com.api.vaccinationmanagement.exception.NotFoundException;
import com.api.vaccinationmanagement.model.SickModel;
import com.api.vaccinationmanagement.model.VaccineModel;
import com.api.vaccinationmanagement.repository.SickRepo;
import com.api.vaccinationmanagement.repository.VaccineRepo;
import com.api.vaccinationmanagement.service.VaccineService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class VaccineServiceImp implements VaccineService {
    @Autowired
    private VaccineRepo vaccineRepo;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private SickRepo sickRepo;

    @Override
    public Page<VaccineModel> findByFilters(String name, String madeIn, Integer age, Integer sickId, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<VaccineModel> filtersQuery = criteriaBuilder.createQuery(VaccineModel.class);
        Root<VaccineModel> vaccineModelRoot = filtersQuery.from(VaccineModel.class);
        filtersQuery.select(vaccineModelRoot);
        Predicate filtersPredicate = predicateBuild(criteriaBuilder, vaccineModelRoot, name, madeIn, age, sickId);
        filtersQuery.where(filtersPredicate).orderBy(criteriaBuilder.asc(vaccineModelRoot.get("name")));
        // Thuc hien count
        TypedQuery<VaccineModel> vaccineModelTypedCount = entityManager.createQuery(filtersQuery);
        // Thuc hien query
        TypedQuery<VaccineModel> vaccineModelTypedQuery = entityManager.createQuery(filtersQuery);
        // Thuc hien phan trang
        vaccineModelTypedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        vaccineModelTypedQuery.setMaxResults(pageable.getPageSize());

        List<VaccineModel> listResults = vaccineModelTypedQuery.getResultList();
        int totalResults = vaccineModelTypedCount.getResultList().size();

        return new PageImpl<>(listResults, pageable, totalResults);
    }

    @Override
    public Optional<VaccineModel> findById(Integer id) {
        Optional<VaccineModel> vaccineModel = vaccineRepo.findById(id);
        if (vaccineModel.isPresent())
            return vaccineModel;
        else
            throw new NotFoundException("Not found vaccine with id: " + id);
    }

    @Override
    public VaccineModel saveNew(InputVaccineDto dto) {
        Optional<SickModel> sickModel = sickRepo.findById(dto.getSickId());
        if (sickModel.isPresent()) {
            VaccineModel vaccineModel = VaccineConverter.InputToModelCreate(dto);
            vaccineModel.setSickModel(sickModel.get());
            return vaccineRepo.save(vaccineModel);
        } else
            throw new NotFoundException("Not found sick with id: " + dto.getSickId());
    }

    @Override
    public VaccineModel saveUpdate(InputVaccineDto dto) {
        VaccineModel vaccineModel = vaccineRepo.findById(dto.getId())
                .orElseThrow(() -> new NotFoundException("Not found vaccine with id: " + dto.getId()));

        if (!Objects.equals(dto.getSickId(), vaccineModel.getSickModel().getId())) {
            SickModel sick = sickRepo.findById(dto.getSickId())
                    .orElseThrow(() -> new NotFoundException("Not found sick with id: " + dto.getSickId()));
            vaccineModel.setSickModel(sick);
        }

        VaccineConverter.InputToModelUpdate(dto, vaccineModel);
        return vaccineRepo.save(vaccineModel);
    }
    @Override
    public void deleteById(Integer id) {
        Optional<VaccineModel> vaccineModel = vaccineRepo.findById(id);
        if (vaccineModel.isPresent())
            vaccineRepo.deleteById(id);
        else
            throw new NotFoundException("Not found vaccine with id: " + id);
    }

    private Predicate predicateBuild(CriteriaBuilder criteriaBuilder, Root<VaccineModel> vaccineModelRoot, String name, String madeIn, Integer age, Integer sickId) {
        List<Predicate> predicateList = new ArrayList<>();
        // Lọc theo name
        if (name != null && !name.isBlank()) {
            Predicate predicate = criteriaBuilder.like(vaccineModelRoot.get("name"), "%" + name + "%");
            predicateList.add(predicate);
        }
        // Lọc theo madeIn
        if (madeIn != null && !madeIn.isBlank()) {
            Predicate predicate = criteriaBuilder.like(vaccineModelRoot.get("madeIn"), "%" + madeIn + "%");
            predicateList.add(predicate);
        }
        // Lọc theo age
        if (age != null) {
            Predicate minAgepredicate = criteriaBuilder.lessThanOrEqualTo(vaccineModelRoot.get("minAge"), age);
            Predicate maxAgepredicate = criteriaBuilder.greaterThanOrEqualTo(vaccineModelRoot.get("maxAge"), age);
            Predicate predicate = criteriaBuilder.and(minAgepredicate, maxAgepredicate);
            predicateList.add(predicate);
        }
        // Lọc theo sickId
        if (sickId != null) {
            Optional<SickModel> sickModel = sickRepo.findById(sickId);
            if (sickModel.isPresent()) {
                Predicate predicate = criteriaBuilder.equal(vaccineModelRoot.get("sickModel"), sickModel.get());
                predicateList.add(predicate);
            } else
                throw new NotFoundException("Not found sick with id: " + sickId);
        }
        return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
    }

}
