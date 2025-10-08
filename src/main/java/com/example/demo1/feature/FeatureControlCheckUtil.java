package com.example.demo1.feature;

import com.example.demo1.client.FeatureServiceClient;
import com.example.demo1.cache.FeatureCacheManager;
import com.example.demo1.config.FeatureDefaultsConfig;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class FeatureControlCheckUtil {
    
    private static final String CQ_SET_DOC_FEE_CAPITALIZED_Y = "CQ_SET_DOC_FEE_CAPITALIZED_Y";
    // REMOVE EC_INSURANCE_REDESIGN
    private static final FeatureServiceClient featureServiceClient = new FeatureServiceClient();
    private static final FeatureCacheManager cacheManager = new FeatureCacheManager();
    private static final FeatureDefaultsConfig defaultsConfig = new FeatureDefaultsConfig();
    
    public static boolean isCqSetDocFeeCapitalizedWithYValueEnabled(){
        return isFeatureEnabled(CQ_SET_DOC_FEE_CAPITALIZED_Y);
    }
    
    public static boolean isFeatureEnabled(String featureId){
        try {
            Boolean cachedValue = cacheManager.getCachedValue(featureId);
            if (cachedValue != null) {
                return cachedValue;
            }
            FeatureServiceClient.FeatureResponse response = featureServiceClient.getFeatureValue(featureId);
            boolean featureEnabled = response.isEnabled();
            cacheManager.updateCache(featureId, featureEnabled);
            return featureEnabled;
        } catch (FeatureServiceClient.FeatureServiceException e) {
            System.err.println("Error checking feature " + featureId + ": " + e.getMessage());
            return getFallbackValue(featureId);
        }
    }
    private static boolean getFallbackValue(String featureId) {
        if (cacheManager.isFeatureCached(featureId)) {
            Boolean cachedValue = cacheManager.getCachedValue(featureId);
            if (cachedValue != null) {
                return cachedValue;
            }
        }
        return defaultsConfig.getDefaultValue(featureId);
    }
    // REMOVE isEcInsuranceRedesignEnabled()
    // The rest utility methods remain unchanged
}
