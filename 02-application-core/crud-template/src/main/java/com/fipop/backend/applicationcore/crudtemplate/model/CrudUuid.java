package com.fipop.backend.applicationcore.crudtemplate.model;

import lombok.Data;

@Data
public class CrudUuid {
    private String id;
    private String name;
    private String phoneNumber;
    private String zipCode;
    private Integer age;
    private Integer type;
}