package com.fipop.backend.applicationcore.crudtemplate.service;

import com.figpop.backend.fgcore.fgbase.service.BaseService;
import com.fipop.backend.applicationcore.crudtemplate.model.CrudUuid;
import com.fipop.backend.applicationcore.crudtemplate.repository.CrudUuidRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Crud service
 */
@Service
@AllArgsConstructor
public class CrudService extends BaseService<CrudUuidRepository, CrudUuid,Long> {

}
