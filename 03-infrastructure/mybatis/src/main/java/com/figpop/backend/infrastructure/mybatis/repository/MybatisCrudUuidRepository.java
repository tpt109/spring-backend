package com.figpop.backend.infrastructure.mybatis.repository;

import com.figpop.backend.infrastructure.mybatis.generated.entity.CrudUuidEntity;
import com.figpop.backend.infrastructure.mybatis.generated.mapper.CrudUuidMapper;
import com.fipop.backend.applicationcore.crudtemplate.model.CrudUuid;
import com.fipop.backend.applicationcore.crudtemplate.repository.CrudUuidRepository;
import org.springframework.stereotype.Repository;


@Repository
public class MybatisCrudUuidRepository extends MybatisBaseRepository<CrudUuidMapper, CrudUuid, CrudUuidEntity,Long> implements CrudUuidRepository {

}
