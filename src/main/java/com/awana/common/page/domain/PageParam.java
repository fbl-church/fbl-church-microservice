/**
 * Copyright of awana App. All rights reserved.
 */
/**
 * Copyright of Awana App. All rights reserved.
 */
package com.awana.common.page.domain;

import com.awana.common.search.CommonParam;

/**
 * Page Param to add to get request objects.
 * 
 * @author Sam Butler
 * @since January 22, 2023
 */
public interface PageParam extends CommonParam {
    public int getPageSize();

    public void setPageSize(int pageSize);

    public int getRowOffset();

    public void setRowOffset(int rowOffset);
}
