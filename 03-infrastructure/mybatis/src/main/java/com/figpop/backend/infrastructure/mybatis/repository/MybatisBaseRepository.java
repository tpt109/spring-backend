package com.figpop.backend.infrastructure.mybatis.repository;

import com.figpop.backend.fgcore.fgbase.pagination.PageModel;
import com.figpop.backend.fgcore.fgbase.repository.BaseRepository;
import com.figpop.backend.fgcore.fgutils.spring.GenericTypeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class MybatisBaseRepository<M ,T ,E ,S extends Serializable> implements BaseRepository<T,S> {

    @Autowired
    private M mapper;

    protected final Class<?>[] typeArguments = GenericTypeUtils.resolveTypeArguments(getClass(), MybatisBaseRepository.class);

    @SuppressWarnings("unchecked")
    protected Class<M> currentMapperClass() {
        return (Class<M>) this.typeArguments[0];
    }
    @SuppressWarnings("unchecked")
    protected Class<E> currentEntityClass() {
        return (Class<E>) this.typeArguments[2];
    }
    @SuppressWarnings("unchecked")
    protected Class<T> currentModelClass() {
        return (Class<T>) this.typeArguments[1];
    }

    @Override
    public boolean add(T model) {
        Method method = null;
        try {
            E entity = BeanUtils.instantiateClass(currentEntityClass());
            BeanUtils.copyProperties(model, entity);
            method = currentMapperClass().getMethod("insert",currentEntityClass());
            method.invoke(mapper,entity);

            return true;
        } catch(Exception ex) {
            // TODO add logger here
            System.out.println(ex.getMessage());
        }

        return false;

    }

    @Override
    public boolean update(T model) {
        return false;
    }

    @Override
    public Optional<T> findById(S id) {
        return Optional.empty();
    }

    @Override
    public Optional<List<T>> findAll(Map<String, String> filter) {
        return Optional.empty();
    }

    @Override
    public Optional<PageModel<T>> getPaging(Map<String, String> filter) {
        return Optional.empty();
    }

    @Override
    public boolean delete(S id) {
        return false;
    }
}
