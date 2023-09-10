/**
 * Copyright of FBL Church App. All rights reserved.
 */
package com.fbl.app.cache.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.fbl.app.cache.client.domain.CacheItem;

/**
 * Cache Service
 *
 * @author Sam Butler
 * @since 8/2/2020
 */
@Service
public class CacheService {

    @Autowired
    private CacheManager manager;

    /**
     * Gets the current cache manager
     * 
     * @return The current cache manager data
     */
    public List<CacheItem> getCache() {
        List<CacheItem> items = new ArrayList<>();

        for (String name : manager.getCacheNames()) {
            CacheItem i = new CacheItem();
            i.setName(name);
            i.setCache(manager.getCache(name).getNativeCache());
            items.add(i);
        }
        return items;
    }

    /**
     * Gets the current cache
     * 
     * @return The current cache data
     */
    public Cache getCache(String name) {
        return manager.getCache(name);
    }

    /**
     * Clear the current cache
     */
    public void clearCache() {
        for (String name : manager.getCacheNames()) {
            manager.getCache(name).clear();
        }
    }
}
