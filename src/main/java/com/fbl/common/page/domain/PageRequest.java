package com.fbl.common.page.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Base Page Request object to add to get request objects.
 * 
 * @author Sam Butler
 * @since Jul 16, 2024
 */
@Getter
@Setter
public class PageRequest implements PageParam {

    @Schema(description = "Row Offset for pagenation.")
    private Integer rowOffset;

    @Schema(description = "Page Size for pagenation result.")
    private Integer pageSize;
}
