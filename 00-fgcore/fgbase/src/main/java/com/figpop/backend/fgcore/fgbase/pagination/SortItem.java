package com.figpop.backend.fgcore.fgbase.pagination;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SortItem implements Serializable {
    private static final long serialVersionUID = 1L;
    private String column;
    private boolean asc = true;

    public static SortItem asc(String column) {
        return build(column, true);
    }

    public static SortItem desc(String column) {
        return build(column, false);
    }

    public static List<SortItem> ascs(String... columns) {
        return (List<SortItem>) Arrays.stream(columns).map(SortItem::asc).collect(Collectors.toList());
    }

    public static List<SortItem> descs(String... columns) {
        return (List<SortItem>)Arrays.stream(columns).map(SortItem::desc).collect(Collectors.toList());
    }

    private static SortItem build(String column, boolean asc) {
        return new SortItem(column, asc);
    }

    public String getColumn() {
        return this.column;
    }

    public boolean isAsc() {
        return this.asc;
    }

    public void setColumn(final String column) {
        this.column = column;
    }

    public void setAsc(final boolean asc) {
        this.asc = asc;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof SortItem)) {
            return false;
        } else {
            SortItem other = (SortItem)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.isAsc() != other.isAsc()) {
                return false;
            } else {
                Object this$column = this.getColumn();
                Object other$column = other.getColumn();
                if (this$column == null) {
                    if (other$column != null) {
                        return false;
                    }
                } else if (!this$column.equals(other$column)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SortItem;
    }

    public int hashCode() {
        int PRIME = 1;
        int result = 1;
        result = result * 59 + (this.isAsc() ? 79 : 97);
        Object $column = this.getColumn();
        result = result * 59 + ($column == null ? 43 : $column.hashCode());
        return result;
    }

    public String toString() {
        return "OrderItem(column=" + this.getColumn() + ", asc=" + this.isAsc() + ")";
    }

    public SortItem() {
    }

    public SortItem(final String column, final boolean asc) {
        this.column = column;
        this.asc = asc;
    }
}
