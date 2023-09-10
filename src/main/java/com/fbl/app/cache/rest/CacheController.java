/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.cache.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fbl.app.cache.client.domain.CacheItem;
import com.fbl.app.cache.service.CacheService;
import com.fbl.common.annotations.interfaces.HasAccess;
import com.fbl.common.annotations.interfaces.RestApiController;
import com.fbl.common.enums.WebRole;

/**
 * Gets feature access for a user
 *
 * @author Sam Butler
 * @since 8/3/2020
 */
@RequestMapping("/api/cache")
@RestApiController
public class CacheController {

    @Autowired
    private CacheService service;

    /**
     * Gets a list of the whole cache
     * 
     * @return The current cache data
     */
    @GetMapping
    @HasAccess(WebRole.SITE_ADMINISTRATOR)
    public List<CacheItem> getCacheManager() {
        return service.getCache();
    }

    /**
     * Gets the current cache
     * 
     * @return The current cache data
     */
    @GetMapping(value = "/{name}")
    @HasAccess(WebRole.SITE_ADMINISTRATOR)
    public Cache getCache(@PathVariable String name) {
        return service.getCache(name);
    }

    /**
     * Clear the current cache
     */
    @DeleteMapping
    @HasAccess(WebRole.ADMINISTRATOR)
    public void clearCache() {
        service.clearCache();
    }
}
