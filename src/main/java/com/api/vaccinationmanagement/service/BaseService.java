package com.api.vaccinationmanagement.service;

import com.api.vaccinationmanagement.model.SickModel;

import java.util.Optional;

public interface BaseService<O,I> {
    Optional<O> findById(Integer id);
//    T saveNew(T model);
//    T saveUpdate(T model);
    O saveNew(I model);
    O saveUpdate(I model);
    void deleteById(Integer id);
}
