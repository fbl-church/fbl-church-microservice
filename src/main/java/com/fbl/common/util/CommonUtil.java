/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.common.util;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.fbl.common.enums.TextEnum;
import com.fbl.common.page.Page;
import com.fbl.common.page.domain.PageParam;
import com.fbl.common.search.SearchParam;

/**
 * Class for storing common methods for use accross the application.
 * 
 * @author Sam Butler
 * @since April 21, 2023
 */
public class CommonUtil {

    /**
     * Method that will simply generate a random 10 digit number based on the local
     * time.
     * 
     * @return {@link Long} of the random number.
     */
    public static long generateRandomNumber() {
        return generateRandomNumber(10);
    }

    /**
     * Generates a random string value
     * 
     * @param length The size of the random string
     * @return the random string
     */
    public static String generateRandomString(int length) {
        return RandomStringUtils.random(length, 48, 122, true, true);
    }

    /**
     * Method that will simply generate a random number based on the given length
     * that is desired.
     * 
     * @param length The length of the random number.
     * @return {@link Long} of the random number.
     */
    public static long generateRandomNumber(int length) {
        Assert.isTrue(length > 0 && length < 11, "Length must be between 0 and 10");

        long numberThreshold = Long.parseLong("9" + "0".repeat(length - 1));
        long mask = Long.parseLong("1" + "0".repeat(length - 1));
        return (long) Math.floor(Math.random() * numberThreshold) + mask;
    }

    /**
     * Converts an enum list into a page request.
     * 
     * @param list    The list of enum values
     * @param request The page request param
     * @return The page Request.
     */
    public static <T extends TextEnum> Page<T> enumListToPage(List<T> list, PageParam request) {
        if (request != null && request instanceof SearchParam) {
            SearchParam searchParam = (SearchParam) request;
            if (searchParam.getSearch() != null && searchParam.getSearch().size() > 0) {
                list = filterPredicate(list, searchParam.getSearch());
            }
        }

        return listToPage(list, request);
    }

    /**
     * Converts a list into a page based on the given page param.
     * 
     * @param list    The list of enum values
     * @param request The page request param
     * @return The page Request.
     */
    public static <T> Page<T> listToPage(List<T> list, PageParam request) {
        int totalCount = list.size();
        if (validPageParamRequest(request) && request.getPageSize() > 0 && totalCount > request.getPageSize()) {
            int startSlice = (int) request.getRowOffset();
            int endSlice = (int) (request.getRowOffset() + request.getPageSize());
            list = list.subList(startSlice, endSlice > totalCount ? totalCount : endSlice);
        }
        return Page.of(totalCount, list);
    }

    /**
     * Convert a string list into a comma seperated list of values.
     * 
     * @param values    The list of values
     * @param seperator The seperator
     * @return String of values
     */
    public static String serializeStringList(List<String> values, String seperator) {
        if (CollectionUtils.isEmpty(values)) {
            return null;
        }
        return String.join(seperator, values);
    }

    /**
     * Convert a string list into a comma seperated list of values.
     * 
     * @param values    The list of values
     * @param seperator The seperator
     * @return String of values
     */
    public static <T extends TextEnum> String serializeEnumList(List<T> values, String seperator) {
        if (CollectionUtils.isEmpty(values)) {
            return null;
        }

        return serializeStringList(values.stream().map(TextEnum::getTextId).toList(), seperator);
    }

    public static String formatPhone(String phoneString) {
        String phoneFiltered = phoneString.replaceAll("\\D+", "");
        if (phoneFiltered.length() != 10) {
            return null;
        }

        return String.format("(%s) %s-%s", phoneFiltered.substring(0, 3), phoneFiltered.substring(3, 6),
                phoneFiltered.substring(6, 10));
    }

    public static Set<String> formatSearch(Set<String> phoneSet) {
        if (CollectionUtils.isEmpty(phoneSet)) {
            return null;
        }

        return phoneSet.stream().map(s -> {
            String phoneFiltered = s.replaceAll("\\D+", "");
            if (phoneFiltered.length() != 10) {
                return s;
            }

            return String.format("(%s) %s-%s", phoneFiltered.substring(0, 3), phoneFiltered.substring(3, 6),
                    phoneFiltered.substring(6, 10));
        }).collect(Collectors.toSet());
    }

    /**
     * Filters out base enums and will perform search on enum list.
     * 
     * @param list   The list to fitler
     * @param search The search to filter the list on
     * @return Filtered list
     */
    private static <T extends TextEnum> List<T> filterPredicate(List<T> list, Set<String> search) {
        return list.stream()
                .filter(r -> search.stream().filter(s -> r.getTextId().contains(s.toUpperCase())).findAny().isPresent())
                .collect(Collectors.toList());
    }

    /**
     * Determines if the page param is valid
     * 
     * @param request The passed in page param
     * @return If it has valid page size and row offset return true, otherwise
     *         false.
     */
    private static boolean validPageParamRequest(PageParam request) {
        return request != null && request.getPageSize() != null && request.getRowOffset() != null;
    }
}
