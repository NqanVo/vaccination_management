package com.api.vaccinationmanagement.service;

import com.api.vaccinationmanagement.model.SickModel;

import java.util.Optional;

public interface BaseService<T> {
    Optional<T> findById(Integer id);
    T saveNew(T model);
    T saveUpdate(T model);
    void deleteById(Integer id);
}
