package com.fipop.backend.applicationcore.crudtemplate.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CrudUuid {
    private String id;
    private String name;
    private Integer grade;
    private Date birthday;
    private BigDecimal walletBalance;
    private String richText;
    private Object jsonData;
    private String base64Data;
//    private String createBy;
//    private String createTime;
//    private String updateBy;
//    private String updateTime;
    private String phoneNumber;
}