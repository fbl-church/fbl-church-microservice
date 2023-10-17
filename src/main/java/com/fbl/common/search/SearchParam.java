/**
 * Copyright of FBL Church App. All rights reserved.
 */
/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.common.search;

import java.util.Set;

/**
 * Search param for setting a value to search for.
 * 
 * @author Sam Butler
 * @since February 2, 2022
 */
public interface SearchParam extends CommonParam {
    Set<String> getSearch();

    void setSearch(Set<String> search);
}
