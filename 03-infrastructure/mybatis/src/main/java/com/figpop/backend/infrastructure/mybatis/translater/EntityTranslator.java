package com.figpop.backend.infrastructure.mybatis.translater;

import com.figpop.backend.infrastructure.mybatis.generated.entity.CrudUuidEntity;
import com.fipop.backend.applicationcore.crudtemplate.model.CrudUuid;
import org.springframework.beans.BeanUtils;

public class EntityTranslator {

  public static CrudUuid crudEntityTranslate(CrudUuidEntity entity) {
    CrudUuid model = new CrudUuid();
    BeanUtils.copyProperties(entity, model);
    return model;
  }

  public static CrudUuidEntity createCrudEntity(CrudUuid model) {
    CrudUuidEntity entity = new CrudUuidEntity();
    BeanUtils.copyProperties(model, entity);
    return entity;
  }
}
