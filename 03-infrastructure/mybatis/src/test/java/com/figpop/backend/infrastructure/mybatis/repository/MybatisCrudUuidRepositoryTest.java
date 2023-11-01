package com.figpop.backend.infrastructure.mybatis.repository;

import com.figpop.backend.fgcore.fgutils.generator.UUIDGenerator;
import com.figpop.backend.infrastructure.mybatis.MybatisApplication;
import com.fipop.backend.applicationcore.crudtemplate.model.CrudUuid;
import com.fipop.backend.applicationcore.crudtemplate.repository.CrudUuidRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MybatisApplication.class)
class MybatisCrudUuidRepositoryTest {

    @Autowired
    CrudUuidRepository repository;

    @Test
    void add() {
        CrudUuid model = new CrudUuid();

        String id = UUIDGenerator.generate();

        model.setId(id);
        model.setName("eweew");
        model.setAge(20);
        model.setType(1);
        model.setZipCode("ewewe");
        model.setPhoneNumber("32932939232");

        assertTrue(repository.add(model));
    }

    @Test
    void update() {
        CrudUuid model = new CrudUuid();
        model.setName("toantp");
        model.setAge(20);
        model.setType(1);
        model.setZipCode("ewewe");
        model.setPhoneNumber("32932939232");

        assertTrue(repository.update(model,"a1b2c3d4"));
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {

        Map<String,Object> filterMap = new HashMap<>();
        filterMap.put("name","name1");
        List<CrudUuid> crudUuids = repository.findAll(filterMap).orElseThrow();

       assertEquals(1,crudUuids.size()); ;
    }

    @Test
    void getPaging() {
    }

    @Test
    void delete() {
    }
}