package com.fbl.app.cache.client.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Class to create a cache item object
 * 
 * @author Sam Butler
 * @since June 25, 2020
 */
@Getter
@Setter
@Schema(description = "The cache item object.")
public class CacheItem {

    @Schema(description = "The unique name of the cache.")
    private String name;

    @Schema(description = "The cache contents")
    private Object cache;
}