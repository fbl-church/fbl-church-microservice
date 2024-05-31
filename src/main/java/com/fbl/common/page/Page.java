/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.common.page;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import io.jsonwebtoken.lang.Assert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    public <R> Page<? extends R> map(Function<? super T, ? extends R> mapper) {
        List<? extends R> mappedList = list.stream().map(mapper).toList();
        return Page.of(mappedList);
    }
}
