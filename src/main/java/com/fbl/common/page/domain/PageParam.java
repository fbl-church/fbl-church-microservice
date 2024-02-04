/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.common.page.domain;

import com.fbl.common.search.CommonParam;

/**
 * Page Param to add to get request objects.
 * 
 * @author Sam Butler
 * @since January 22, 2023
 */
public interface PageParam extends CommonParam {
    public Integer getPageSize();

    public void setPageSize(Integer pageSize);

    public Integer getRowOffset();

    public void setRowOffset(Integer rowOffset);
}
