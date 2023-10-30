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
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void getPaging() {
    }

    @Test
    void delete() {
    }
}