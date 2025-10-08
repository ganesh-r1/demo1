package com.example.demo1.cache;

import org.springframework.stereotype.Component;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Component
public class FeatureCacheManager {
    
    private static final long DEFAULT_CACHE_TTL_MS = 60000; // 1 minute cache
    
    private final Map<String, Boolean> featureCache = new ConcurrentHashMap<>();
    private final Map<String, Long> cacheTimestamps = new ConcurrentHashMap<>();
    private final long cacheTtlMs;
    
    public FeatureCacheManager() {
        this.cacheTtlMs = DEFAULT_CACHE_TTL_MS;
    }
    
    public FeatureCacheManager(long cacheTtlMs) {
        this.cacheTtlMs = cacheTtlMs;
    }
    
    /**
     * Check if cached value is valid for the given feature
     */
    public boolean isCacheValid(String featureId) {
        if (!featureCache.containsKey(featureId) || !cacheTimestamps.containsKey(featureId)) {
            return false;
        }
        
        long cacheTime = cacheTimestamps.get(featureId);
        return (System.currentTimeMillis() - cacheTime) < cacheTtlMs;
    }
    
    /**
     * Get cached feature value
     */
    public Boolean getCachedValue(String featureId) {
        if (isCacheValid(featureId)) {
            return featureCache.get(featureId);
        }
        return null;
    }
    
    /**
     * Update cache with new feature value
     */
    public void updateCache(String featureId, boolean value) {
        featureCache.put(featureId, value);
        cacheTimestamps.put(featureId, System.currentTimeMillis());
    }
    
    /**
     * Get cached value or default if not available
     */
    public boolean getCachedOrDefault(String featureId, boolean defaultValue) {
        Boolean cachedValue = getCachedValue(featureId);
        return cachedValue != null ? cachedValue : defaultValue;
    }
    
    /**
     * Clear all cached values
     */
    public void clearCache() {
        featureCache.clear();
        cacheTimestamps.clear();
    }
    
    /**
     * Clear specific feature from cache
     */
    public void clearFeature(String featureId) {
        featureCache.remove(featureId);
        cacheTimestamps.remove(featureId);
    }
    
    /**
     * Get cache statistics
     */
    public CacheStats getCacheStats() {
        return new CacheStats(
            featureCache.size(),
            cacheTtlMs,
            featureCache.keySet(),
            System.currentTimeMillis()
        );
    }
    
    /**
     * Check if feature is cached (regardless of validity)
     */
    public boolean isFeatureCached(String featureId) {
        return featureCache.containsKey(featureId);
    }
    
    /**
     * Get cache age for a specific feature in milliseconds
     */
    public long getCacheAge(String featureId) {
        Long timestamp = cacheTimestamps.get(featureId);
        if (timestamp == null) {
            return -1;
        }
        return System.currentTimeMillis() - timestamp;
    }
    
    /**
     * Cache statistics object
     */
    public static class CacheStats {
        private final int size;
        private final long ttlMs;
        private final java.util.Set<String> cachedFeatures;
        private final long timestamp;
        
        public CacheStats(int size, long ttlMs, java.util.Set<String> cachedFeatures, long timestamp) {
            this.size = size;
            this.ttlMs = ttlMs;
            this.cachedFeatures = new java.util.HashSet<>(cachedFeatures);
            this.timestamp = timestamp;
        }
        
        public int getSize() { return size; }
        public long getTtlMs() { return ttlMs; }
        public java.util.Set<String> getCachedFeatures() { return cachedFeatures; }
        public long getTimestamp() { return timestamp; }
        
        @Override
        public String toString() {
            return String.format("CacheStats{size=%d, ttlMs=%d, features=%s, timestamp=%d}", 
                    size, ttlMs, cachedFeatures, timestamp);
        }
    }
}
