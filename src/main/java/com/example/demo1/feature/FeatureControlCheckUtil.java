package com.example.demo1.feature;

import com.example.demo1.client.FeatureServiceClient;
import com.example.demo1.cache.FeatureCacheManager;
import com.example.demo1.config.FeatureDefaultsConfig;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class FeatureControlCheckUtil {
    
    private static final String CQ_SET_DOC_FEE_CAPITALIZED_Y = "CQ_SET_DOC_FEE_CAPITALIZED_Y";
    private static final String EC_INSURANCE_REDESIGN = "EC_INSURANCE_REDESIGN";
    
    private static final FeatureServiceClient featureServiceClient = new FeatureServiceClient();
    private static final FeatureCacheManager cacheManager = new FeatureCacheManager();
    private static final FeatureDefaultsConfig defaultsConfig = new FeatureDefaultsConfig();
    
    public static boolean isCqSetDocFeeCapitalizedWithYValueEnabled() {
        // Feature is now always enabled
        return true;
    }
    
    public static boolean isFeatureEnabled(String featureId){
        if (CQ_SET_DOC_FEE_CAPITALIZED_Y.equals(featureId)) {
            return true;
        }
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
        if (CQ_SET_DOC_FEE_CAPITALIZED_Y.equals(featureId)) {
            return true;
        }
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
    
    public static void clearFeatureCache() {
        cacheManager.clearCache();
    }
    
    public static Map<String, Object> getCacheStatus() {
        FeatureCacheManager.CacheStats stats = cacheManager.getCacheStats();
        return Map.of(
            "cached_features", stats.getCachedFeatures(),
            "cache_size", stats.getSize(),
            "cache_ttl_ms", stats.getTtlMs(),
            "timestamp", stats.getTimestamp()
        );
    }
    
    public static boolean isFeatureServiceAvailable() {
        return featureServiceClient.isServiceAvailable();
    }
    
    public static FeatureDefaultsConfig.FeatureMetadata getFeatureMetadata(String featureId) {
        return defaultsConfig.getFeatureMetadata(featureId);
    }
    
    public static java.util.Set<String> getKnownFeatures() {
        return defaultsConfig.getKnownFeatureIds();
    }
    
    public static boolean refreshFeature(String featureId) {
        if (CQ_SET_DOC_FEE_CAPITALIZED_Y.equals(featureId)) {
            return true;
        }
        try {
            cacheManager.clearFeature(featureId);
            return isFeatureEnabled(featureId);
        } catch (Exception e) {
            System.err.println("Error refreshing feature " + featureId + ": " + e.getMessage());
            return defaultsConfig.getDefaultValue(featureId);
        }
    }
}
