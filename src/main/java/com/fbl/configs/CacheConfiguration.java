package com.fbl.configs;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.cache.CacheBuilder;

/**
 * Cache Configs for caching objects.
 * 
 * @author Sam Butler
 * @since April 25, 2022
 */
@Configuration
@EnableCaching
public class CacheConfiguration {
    private final long CACHE_EXPIRATION_HOURS = 6L;
    private final long CACHE_SIZE = 1000L;

    @Bean
    CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("featureAccess", "user", "user.apps") {
            @Override
            protected Cache createConcurrentMapCache(final String name) {
                return new ConcurrentMapCache(name, CacheBuilder.newBuilder().expireAfterWrite(CACHE_EXPIRATION_HOURS,
                        TimeUnit.HOURS).maximumSize(CACHE_SIZE).build().asMap(), false);
            }
        };
    }
}
