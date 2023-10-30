package com.figpop.backend.fgcore.fgbase.pagination;

import com.google.common.base.CaseFormat;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.StringUtils;

import java.util.*;

public final class PaginationUtils {

    private static final int DEFAULT_RECORD_PER_PAGE = 20;
    private static final int DEFAULT_CURRENT_PAGE = 0;

    public static final String DEFAULT_SORT_PROPERTY = "id";
    public static final String SORT_COLUMN_PROPERTY = "sortColumn";
    public static final String SORT_TYPE_PROPERTY = "sortType";
    public static final String ASC = "asc";

    private PaginationUtils() {
    }

    public static PageRequest generatePageRequest(final String currentPage,
                                                  final String pageSize,
                                                  final String sortColumn,
                                                  final String sortType) {

        List<SortItem> sortItems = new ArrayList<>();
        SortItem sortItem = new SortItem();
        sortItem.setColumn(sortColumn);
        sortItem.setAsc(ASC.equalsIgnoreCase(sortType));
        sortItems.add(sortItem);

        return new PageRequest(Integer.valueOf(currentPage),Integer.valueOf(pageSize),sortItems);
    }

    public static PageRequest generatePageRequest(final String currentPage, final String pageSize) {

        return new PageRequest(Integer.valueOf(currentPage),Integer.valueOf(pageSize),null);
    }

    public static PageRequest generatePageRequest(final Map<String, String> params) {

        String sortConditions = params.get(SORT_COLUMN_PROPERTY) == null ?
                String.format("%s-%s", DEFAULT_SORT_PROPERTY, ASC) : params.get(SORT_COLUMN_PROPERTY);
        List<SortItem> sortItems = new ArrayList<>();
        for (String sortCondition : sortConditions.split(",")) {
            String[] sortArr = sortCondition.split("-");
            String sortField = sortArr[0];
            String sortType = sortArr.length > 1 ? sortArr[1] : ASC;
            SortItem sortItem = new SortItem();
            sortItem.setColumn(sortField);
            sortItem.setAsc(ASC.equalsIgnoreCase(sortType));
            sortItems.add(sortItem);
        }

        String page = params.get("page") == null ? String.valueOf(DEFAULT_CURRENT_PAGE) : params.get("page");
        String limit = params.get("limit") == null ? String.valueOf(DEFAULT_RECORD_PER_PAGE) : params.get("limit");

        return new PageRequest(Integer.parseInt(page),Integer.parseInt(limit),sortItems);
    }

//    private static <T> Integer getPrev(final Page<T> page) {
//        return page.hasPrevious() ? page.getNumber() - 1 : null;
//    }
//
//    private static <T> Integer getNext(final Page<T> page) {
//        return page.hasNext() ? page.getNumber() + 1 : null;
//    }

    private static Integer getPage(final String currentPage) {
        if (!StringUtils.hasText(currentPage)) {
            return DEFAULT_CURRENT_PAGE;
        }

        int page;
        try {
            page = Integer.parseInt(currentPage);
            page = page > 0 ? page - 1 : DEFAULT_CURRENT_PAGE;
        } catch (NumberFormatException e) {
            page = DEFAULT_CURRENT_PAGE;
        }

        return page;
    }

    private static Integer getSize(final String pageSize) {
        if (!StringUtils.hasText(pageSize)) {
            return DEFAULT_RECORD_PER_PAGE;
        }

        int size;
        try {
            size = Integer.parseInt(pageSize);
            // size = Math.max(size, DEFAULT_RECORD_PER_PAGE);
        } catch (NumberFormatException e) {
            size = DEFAULT_RECORD_PER_PAGE;
        }

        return size;
    }

    private static String[] parseProperties(final String sortColumn) {
        return Optional.ofNullable(sortColumn)
                       .map((c) -> Arrays.stream(StringUtils.commaDelimitedListToStringArray(c))
                                         .map(PaginationUtils::underscoreToUppercase)
                                         .toArray(String[]::new)).orElse(new String[]{DEFAULT_SORT_PROPERTY});
    }

    private static String underscoreToUppercase(final String value) {
        if (StringUtils.hasText(value) && value.contains("_")) {
            String upper = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, value);
            return upper.isEmpty() ? Strings.EMPTY : Character.toLowerCase(upper.charAt(0)) + upper.substring(1);
        }

        return value;
    }
}
