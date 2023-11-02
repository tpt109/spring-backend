package com.figpop.backend.infrastructure.mybatis.repository;

import com.figpop.backend.fgcore.fgbase.pagination.PageModel;
import com.figpop.backend.fgcore.fgbase.pagination.PageRequest;
import com.figpop.backend.fgcore.fgbase.pagination.PaginationUtils;
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
        model.setName("name add");
        model.setAge(33);
        model.setType(2);
        model.setZipCode("000-222");
        model.setPhoneNumber("32932939232");

        assertTrue(repository.add(model));
    }

    @Test
    void update() {
        CrudUuid model = new CrudUuid();
        model.setName("updatename");
        model.setAge(20);
        model.setType(1);
        model.setZipCode("333-333");
        model.setPhoneNumber("32932939232");

        assertTrue(repository.update(model,"a1b2c3d4"));
    }

    @Test
    void findById() {
    }

    @Test
    void findAllByName() {
        Map<String,Object> filterMap = new HashMap<>();
        filterMap.put("name==","toantp");
        List<CrudUuid> crudUuids = repository.findAll(filterMap).orElseThrow();
       assertEquals(1,crudUuids.size()); ;
    }

    @Test
    void findAllByAge() {
        Map<String,Object> filterMap = new HashMap<>();
        filterMap.put("age==",36);
        List<CrudUuid> crudUuids = repository.findAll(filterMap).orElseThrow();
        assertEquals(1,crudUuids.size()); ;
    }

    @Test
    void findAllByAgeAndName() {

        Map<String,Object> filterMap = new HashMap<>();
        filterMap.put("age==",36);
        filterMap.put("name==","toantp");
        List<CrudUuid> crudUuids = repository.findAll(filterMap).orElseThrow();
        assertEquals(1,crudUuids.size()); ;
    }

    @Test
    void findAllByContainName() {
        Map<String,Object> filterMap = new HashMap<>();
        filterMap.put("nameLIKE","oan");
        List<CrudUuid> crudUuids = repository.findAll(filterMap).orElseThrow();
        assertEquals(1,crudUuids.size()); ;
    }

    @Test
    void findAllWithoutCondition() {
        List<CrudUuid> crudUuids = repository.findAll(null).orElseThrow();
        assertEquals(2,crudUuids.size()); ;
    }

    @Test
    void getPaging() {

        Map<String,Object> filterMap = new HashMap<>();

        Map<String,String> pageRequestMap = new HashMap<>();
        pageRequestMap.put("page","0");
        pageRequestMap.put("limit","2");

        PageRequest pageRequest = PaginationUtils.generatePageRequest(pageRequestMap);
        filterMap.put("nameLIKE","oan");
        PageModel<CrudUuid> crudUuidPageModel = repository.getPaging(filterMap,pageRequest).orElseThrow();
        assertEquals(1,crudUuidPageModel.getRecords().size()); ;
    }

    @Test
    void delete() {
        repository.delete("a1b2c3d4");
    }
}