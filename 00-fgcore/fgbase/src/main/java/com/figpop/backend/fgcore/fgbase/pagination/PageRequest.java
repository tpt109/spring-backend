package com.figpop.backend.fgcore.fgbase.pagination;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode
@ToString
public class PageRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -4541509938956089562L;

    private int pageNumber;
    private int pageSize;
    private List<SortItem> sortItems = new ArrayList<>();


    public PageRequest(int pageNumber, int pageSize, List<SortItem> sortItems) {

        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sortItems = sortItems;
    }

    public PageRequest next() {
        return new PageRequest(getPageNumber() + 1, getPageSize(), this.sortItems);
    }

    public PageRequest previous() {
        return getPageNumber() == 0 ? this : new PageRequest(getPageNumber() - 1, getPageSize(), this.sortItems);
    }

    public PageRequest first() {
        return new PageRequest(0, getPageSize(), this.sortItems);
    }

    public PageRequest withPage(int pageNumber) {
        return new PageRequest(pageNumber, getPageSize(), this.sortItems);
    }

    public PageRequest withSort(List<SortItem> sortItems) {
        return new PageRequest(getPageNumber(), getPageSize(), sortItems);
    }
}
