package com.figpop.backend.fgcore.fgbase.pagination;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

import java.util.List;

@Data
@JsonRootName("data")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageModel<T> {
    private Long total;
    private Integer limit;
    private Integer totalPage;
    private Integer page;
    private List<T> records;
}
