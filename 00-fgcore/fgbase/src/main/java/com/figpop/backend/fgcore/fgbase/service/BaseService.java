package com.figpop.backend.fgcore.fgbase.service;

import com.figpop.backend.fgcore.fgbase.pagination.PageModel;
import com.figpop.backend.fgcore.fgbase.repository.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BaseService <R extends BaseRepository<T,S>,T , S extends Serializable> {

    @Autowired
    private R baseRepository ;

    boolean add(T model) {
        return baseRepository.add(model);
    }
    boolean update(T model) {
        return baseRepository.update(model);
    }

    Optional<T> findById(S id) {
        return baseRepository.findById(id);
    }

    Optional<List<T>> findAll(Map<String,String> filter){
        return baseRepository.findAll(filter);
    }

    Optional<PageModel<T>> getPaging(Map<String,String> filter) {
        return baseRepository.getPaging(filter);
    }

    boolean delete(S id) {
        return baseRepository.delete(id);
    }
}
