package com.figpop.backend.fgcore.fgbase.repository;

import com.figpop.backend.fgcore.fgbase.pagination.PageModel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BaseRepository<T,PK extends Serializable> {

    /**
     * Add crud
     *
     * @param T object
     * @return boolean information
     */
    boolean add(T model);

    /**
     * update crud
     *
     * @param T object
     * @return updated crud object information
     */
    boolean update(T model,PK id);

    /**
     * Gets the T information for the specified ID.
     *
     * @param id Id
     * @return T information. Optional, empty if not present.
     */
    Optional<T> findById(PK id);

    /**
     * Gets list T information for the specified ID.
     *
     * @param filter
     * @return T information. Optional, empty if not present.
     */
    Optional<List<T>> findAll(Map<String,Object> filter);

    /**
     * Gets list T information for the specified ID.
     *
     * @param filter
     * @return T information. Optional, empty if not present.
     */
    Optional<PageModel<T>> getPaging(Map<String,String> filter);

    /**
     * update crud
     *
     * @return success / false
     */
    boolean delete(PK id);
}
