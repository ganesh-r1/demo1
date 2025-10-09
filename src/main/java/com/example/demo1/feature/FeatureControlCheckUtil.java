package com.example.demo1.feature;

import com.example.demo1.client.FeatureServiceClient;
import com.example.demo1.cache.FeatureCacheManager;
import com.example.demo1.config.FeatureDefaultsConfig;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class FeatureControlCheckUtil {
    
    private static final String EC_INSURANCE_REDESIGN = "EC_INSURANCE_REDESIGN";
    
    private static final FeatureServiceClient featureServiceClient = new FeatureServiceClient();
    private static final FeatureCacheManager cacheManager = new FeatureCacheManager();
    private static final FeatureDefaultsConfig defaultsConfig = new FeatureDefaultsConfig();
    
    // Always return true for the removed flag
    public static boolean isCqSetDocFeeCapitalizedWithYValueEnabled(){
        return true;
    }
    
    public static boolean isFeatureEnabled(String featureId){
        try {
            // Check cache first
            Boolean cachedValue = cacheManager.getCachedValue(featureId);
            if (cachedValue != null) {
                return cachedValue;
            }
            
            // Fetch from remote service
            FeatureServiceClient.FeatureResponse response = featureServiceClient.getFeatureValue(featureId);
            boolean featureEnabled = response.isEnabled();
            
            // Update cache
            cacheManager.updateCache(featureId, featureEnabled);
            
            return featureEnabled;
            
        } catch (FeatureServiceClient.FeatureServiceException e) {
            System.err.println("Error checking feature " + featureId + ": " + e.getMessage());
            
            // Fallback strategy: cache -> default -> false
            return getFallbackValue(featureId);
        }
    }
    
    private static boolean getFallbackValue(String featureId) {
        // Try cached value first (even if expired)
        if (cacheManager.isFeatureCached(featureId)) {
            Boolean cachedValue = cacheManager.getCachedValue(featureId);
            if (cachedValue != null) {
                return cachedValue;
            }
        }
        
        // Fall back to configured default
        return defaultsConfig.getDefaultValue(featureId);
    }
    
    public static boolean isEcInsuranceRedesignEnabled(){
        return isFeatureEnabled(EC_INSURANCE_REDESIGN);
    }
    
    // Utility method to clear cache (useful for testing)
    public static void clearFeatureCache() {
        cacheManager.clearCache();
    }
    
    // Utility method to get cache status
    public static Map<String, Object> getCacheStatus() {
        FeatureCacheManager.CacheStats stats = cacheManager.getCacheStats();
        return Map.of(
            "cached_features", stats.getCachedFeatures(),
            "cache_size", stats.getSize(),
            "cache_ttl_ms", stats.getTtlMs(),
            "timestamp", stats.getTimestamp()
        );
    }
    
    // Utility method to check service health
    public static boolean isFeatureServiceAvailable() {
        return featureServiceClient.isServiceAvailable();
    }
    
    // Utility method to get feature metadata
    public static FeatureDefaultsConfig.FeatureMetadata getFeatureMetadata(String featureId) {
        return defaultsConfig.getFeatureMetadata(featureId);
    }
    
    // Utility method to get all known features
    public static java.util.Set<String> getKnownFeatures() {
        return defaultsConfig.getKnownFeatureIds();
    }
    
    // Force refresh a specific feature (bypass cache)
    public static boolean refreshFeature(String featureId) {
        try {
            cacheManager.clearFeature(featureId);
            return isFeatureEnabled(featureId);
        } catch (Exception e) {
            System.err.println("Error refreshing feature " + featureId + ": " + e.getMessage());
            return defaultsConfig.getDefaultValue(featureId);
        }
    }
}
