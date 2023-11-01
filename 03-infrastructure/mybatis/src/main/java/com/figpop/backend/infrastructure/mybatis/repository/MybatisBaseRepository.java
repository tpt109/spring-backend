package com.figpop.backend.infrastructure.mybatis.repository;

import com.figpop.backend.fgcore.fgbase.pagination.PageModel;
import com.figpop.backend.fgcore.fgbase.repository.BaseRepository;
import com.figpop.backend.fgcore.fgutils.spring.GenericTypeUtils;
import com.figpop.backend.infrastructure.mybatis.generated.entity.CrudUuidEntityExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;


import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Slf4j
public class MybatisBaseRepository<M ,T ,E ,EE ,PK extends Serializable> implements BaseRepository<T,PK> {

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
    @SuppressWarnings("unchecked")
    protected Class<EE> currentEntityExampleClass() {
        return (Class<EE>) this.typeArguments[3];
    }

    @Override
    public boolean add(T model) {
        try {
            E entity = BeanUtils.instantiateClass(currentEntityClass());
            BeanUtils.copyProperties(model, entity);
            Method method = currentMapperClass().getMethod("insert",currentEntityClass());
            return method.invoke(mapper,entity).toString().equals("1");
        } catch(Exception ex) {
            LOGGER.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean update(T model, PK id) {
        try {
            Method methodSelect = currentMapperClass().getMethod("selectByPrimaryKey",id.getClass());
            @SuppressWarnings("unchecked")
            E entity = (E)methodSelect.invoke(mapper,id);
            BeanUtils.copyProperties(model, entity,"id");
            Method method = currentMapperClass().getMethod("updateByPrimaryKey",currentEntityClass());
            return method.invoke(mapper,entity).toString().equals("1");
        } catch(Exception ex) {
            LOGGER.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Optional<T> findById(PK id) {
        try {
            Method methodSelect = currentMapperClass().getMethod("selectByPrimaryKey",id.getClass());
            @SuppressWarnings("unchecked")
            E entity = (E)methodSelect.invoke(mapper,id);
            T model = BeanUtils.instantiateClass(currentModelClass());
            BeanUtils.copyProperties(entity,model);
            return Optional.of(model);
        } catch(Exception ex) {
            LOGGER.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Optional<List<T>> findAll(Map<String, Object> filter) {
        EE example = null;
        if (filter != null) {
            example = BeanUtils.instantiateClass(currentEntityExampleClass());
            addCriteriaIntoExample(example ,filter);
        }

        try {
            Method methodSelect = currentMapperClass().getMethod("selectByExample",currentEntityExampleClass());
            @SuppressWarnings("unchecked")
            List<E> entityList = (List<E>)methodSelect.invoke(mapper,example);

            if (entityList == null || entityList.isEmpty()) return Optional.empty();

            List<T> modelList = new ArrayList<>();
            entityList.forEach((entityEle)->{
                T modelEle = BeanUtils.instantiateClass(currentModelClass());
                BeanUtils.copyProperties(entityEle,modelEle);
                modelList.add(modelEle);
            });

            return Optional.of(modelList);
        } catch(Exception ex) {
            LOGGER.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    private void addCriteriaIntoExample(EE example ,Map<String, Object> filter) {

        for (Map.Entry<String, Object> entry : filter.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();


            try {
                Method methodGetCriteriaObject = currentEntityExampleClass().getMethod("or");
                Object objectCriteria = methodGetCriteriaObject.invoke(example);

                // TODO filter by equal first
                String fieldName = String.valueOf(key.charAt(0)).toUpperCase() + key.substring(1);
                String conditionName = "EqualTo";
                String methodName = "and" + fieldName + conditionName;

                Method methodCreateCriteria = objectCriteria.getClass().getMethod(methodName, value.getClass());
                methodCreateCriteria.invoke(objectCriteria, value);
            } catch (Exception ex) {
                LOGGER.error(ex.getMessage());
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public Optional<PageModel<T>> getPaging(Map<String, String> filter) {
        return Optional.empty();
    }

    @Override
    public boolean delete(PK id) {
        return false;
    }
}
