package com.api.vaccinationmanagement.service.imp;

import com.api.vaccinationmanagement.exception.NotFoundException;
import com.api.vaccinationmanagement.model.PatientModel;
import com.api.vaccinationmanagement.model.SickModel;
import com.api.vaccinationmanagement.repository.SickRepo;
import com.api.vaccinationmanagement.service.SickService;
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
public class SickServiceImp implements SickService {
    @Autowired
    private SickRepo sickRepo;
    @Autowired
    private EntityManager entityManager;
    @Override
    public Page<SickModel> findByFilters(String name, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<SickModel> filtersQuery = criteriaBuilder.createQuery(SickModel.class);
        Root<SickModel> sickModelRoot = filtersQuery.from(SickModel.class);
        filtersQuery.select(sickModelRoot);
        Predicate filtersPredicate = predicateBuild(criteriaBuilder, sickModelRoot, name);
        filtersQuery.where(filtersPredicate).orderBy(criteriaBuilder.asc(sickModelRoot.get("name")));
        // Thuc hien count
        TypedQuery<SickModel> sickModelTypedCount = entityManager.createQuery(filtersQuery);
        // Thuc hien query
        TypedQuery<SickModel> sickModelTypedQuery = entityManager.createQuery(filtersQuery);
        // Thuc hien phan trang
        sickModelTypedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        sickModelTypedQuery.setMaxResults(pageable.getPageSize());

        List<SickModel> listResults = sickModelTypedQuery.getResultList();
        int totalResults = sickModelTypedCount.getResultList().size();

        return new PageImpl<>(listResults, pageable, totalResults);
    }

    @Override
    public Optional<SickModel> findById(Integer id) {
        Optional<SickModel> sickModel = sickRepo.findById(id);
        if (sickModel.isPresent())
            return sickModel;
        else
            throw new NotFoundException("Not found sick with id: " + id);
    }

    @Override
    public SickModel saveNew(SickModel model) {
        return sickRepo.save(model);
    }

    @Override
    public SickModel saveUpdate(SickModel model) {
        Optional<SickModel> sickModel = sickRepo.findById(model.getId());
        if (sickModel.isPresent())
            return sickRepo.save(model);
        else
            throw new NotFoundException("Not found sick with id: " + model.getId());

    }

    @Override
    public void deleteById(Integer id) {
        Optional<SickModel> sickModel = sickRepo.findById(id);
        if (sickModel.isPresent())
            sickRepo.deleteById(id);
        else
            throw new NotFoundException("Not found sick with id: " + id);
    }

    private Predicate predicateBuild(CriteriaBuilder criteriaBuilder, Root<SickModel> sickModelRoot, String name) {
        List<Predicate> predicateList = new ArrayList<>();
        // Lọc theo name
        if (name != null && !name.isBlank()) {
            Predicate predicate = criteriaBuilder.like(sickModelRoot.get("name"), "%" + name + "%");
            predicateList.add(predicate);
        }
        return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
    }

}
