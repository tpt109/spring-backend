package com.figpop.backend.infrastructure.mybatis.repository;

import com.figpop.backend.fgcore.fgbase.pagination.PageModel;
import com.figpop.backend.fgcore.fgbase.pagination.PageRequest;
import com.figpop.backend.fgcore.fgbase.repository.BaseRepository;
import com.figpop.backend.fgcore.fgutils.contants.SymbolConstant;
import com.figpop.backend.fgcore.fgutils.spring.GenericTypeUtils;
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
        EE example = buildExampleFrom(filter,null);

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

    private EE buildExampleFrom (Map<String, Object> filter, PageRequest pageRequest) {
        EE example = null;
        if (filter != null) {
            example = BeanUtils.instantiateClass(currentEntityExampleClass());
            addCriteriaIntoExample(example ,filter);
        }

        if (pageRequest != null) {
            addPagingIntoExample(example, pageRequest);
        }

        return example;
    }

    private void addCriteriaIntoExample(EE example ,Map<String, Object> filter) {
        try {
            Method methodGetCriteriaObject = currentEntityExampleClass().getMethod("or");
            Object objectCriteria = methodGetCriteriaObject.invoke(example);

            for (Map.Entry<String, Object> entry : filter.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();

                String methodName;
                String firstCharColumn = String.valueOf(key.charAt(0)).toUpperCase();
                if (key.endsWith(SymbolConstant.EQUAL_SQL)) {
                    String fieldName = firstCharColumn + key.substring(1,key.length() - 2);
                    methodName = SymbolConstant.AND_EN + fieldName + SymbolConstant.EQUAL_CONDITION;
                } else if (key.endsWith(SymbolConstant.LIKE_SQL) && value instanceof String) {
                    String fieldName = firstCharColumn + key.substring(1,key.length() - 4);
                    methodName = SymbolConstant.AND_EN + fieldName + SymbolConstant.LIKE_CONDITION;
                    value = SymbolConstant.PERCENT_SIGN + value + SymbolConstant.PERCENT_SIGN;
                } else {
                    LOGGER.error("can't query : " + key + ":" + value);
                    throw new RuntimeException("can't query : " + key + ":" + value);
                }

                Method methodCreateCriteria = objectCriteria.getClass().getMethod(methodName, value.getClass());
                methodCreateCriteria.invoke(objectCriteria, value);

            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    private void addPagingIntoExample(EE example , PageRequest pageRequest) {
        try {
            Method methodSetLimit = currentEntityExampleClass().getMethod("setLimit",Integer.class);
            methodSetLimit.invoke(example,pageRequest.getPageSize());

            Method methodSetOffset = currentEntityExampleClass().getMethod("setOffset",Integer.class);
            methodSetOffset.invoke(example,pageRequest.getPageNumber());
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Optional<PageModel<T>> getPaging(Map<String, Object> filter , PageRequest pageRequest) {

        EE exampleWithPaging = buildExampleFrom(filter,pageRequest);
        EE exampleAll = buildExampleFrom(filter,null);

        try {
            Method methodSelect = currentMapperClass().getMethod("selectByExample",currentEntityExampleClass());
            @SuppressWarnings("unchecked")
            List<E> entityList = (List<E>)methodSelect.invoke(mapper,exampleWithPaging);
            if (entityList == null || entityList.isEmpty()) return Optional.empty();

            // get list data
            List<T> modelList = new ArrayList<>();
            entityList.forEach((entityEle)->{
                T modelEle = BeanUtils.instantiateClass(currentModelClass());
                BeanUtils.copyProperties(entityEle,modelEle);
                modelList.add(modelEle);
            });

            // get total
            Method methodSelectCount = currentMapperClass().getMethod("countByExample",currentEntityExampleClass());
            Long totalRecord = (Long) methodSelectCount.invoke(mapper,exampleAll);

            // convert to paging
            PageModel<T> pageModel = new PageModel<>();
            pageModel.setLimit(pageRequest.getPageSize());
            pageModel.setTotalPage(totalRecord.intValue());
            pageModel.setPage(pageRequest.getPageNumber());
            pageModel.setRecords(modelList);

            return Optional.of(pageModel);
        } catch(Exception ex) {
            LOGGER.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean delete(PK id) {
        try {
            Method method = currentMapperClass().getMethod("deleteByPrimaryKey",id.getClass());
            return method.invoke(mapper,id).toString().equals("1");
        } catch(Exception ex) {
            LOGGER.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
}
