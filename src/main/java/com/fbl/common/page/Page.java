/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.common.page;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import com.fbl.common.page.domain.PageParam;

import io.jsonwebtoken.lang.Assert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Page class to add total count header.
 * 
 * @author Sam Butler
 * @since January 22, 2023
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Schema(description = "Page obejct for holding list and total count.")
public class Page<T> {
    public static final String TOTAL_ITEM_COUNT = "total-count";

    @Schema(description = "Total count of items in the list.")
    private long totalCount;

    @Schema(description = "The list of generic objects.")
    private List<T> list;

    /**
     * Create a new page instance with the given list. It will populate the total
     * count based on the size of the list.
     * 
     * @param <T>  The generic type of the list
     * @param list The list of elements to put on the page
     * @return The new page instance
     */
    public static <T> Page<T> of(List<T> list) {
        Assert.notNull(list, "List can not be null for page");
        return new Page<T>(list.size(), list);
    }

    /**
     * Create a new page instance with the given list.
     * 
     * @param <T>        The generic type of the list
     * @param totalCount The total amount of elements
     * @param list       The list of elements to put on the page
     * @return The new page instance
     */
    public static <T> Page<T> of(long totalCount, List<T> list) {
        Assert.notNull(list, "List can not be null for page");
        return new Page<T>(totalCount, list);
    }

    /**
     * Generate an empty page.
     * 
     * @param <T> The generic type of the list
     * @return An empty page instance
     */
    public static <T> Page<T> empty() {
        return new Page<T>(0, Collections.emptyList());
    }

    /**
     * Creates an unpaged instance of the given class.
     * 
     * @param <T>   The generic type of the page
     * @param clazz The class to create an instance of
     * @return The unpaged instance
     */
    public static <T extends PageParam> T unpaged(Class<T> clazz) {
        try {
            T newInstance = clazz.getDeclaredConstructor().newInstance();
            newInstance.setPageSize(Integer.MAX_VALUE);
            newInstance.setRowOffset(0);
            return newInstance;
        } catch (Exception e) {
            log.warn("Could not create instance of class: '" + clazz.getName() + "' for unpaged page");
            return null;
        }
    }

    /**
     * Gets the size of the page
     * 
     * @return The size of the page
     */
    public int size() {
        return list.size();
    }

    /**
     * For each consumer on the list
     * 
     * @param action
     */
    public void forEach(Consumer<? super T> action) {
        list.forEach(action);
    }

    /**
     * map consumer on the list
     * 
     * @param action
     */
    public <R> Page<R> map(Function<? super T, R> mapper) {
        List<R> mappedList = list.stream().map(mapper).toList();
        return Page.of(mappedList);
    }
}
