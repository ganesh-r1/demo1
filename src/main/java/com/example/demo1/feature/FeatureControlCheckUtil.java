package com.example.demo1.feature;

import com.example.demo1.client.FeatureServiceClient;
import com.example.demo1.cache.FeatureCacheManager;
import com.example.demo1.config.FeatureDefaultsConfig;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class FeatureControlCheckUtil {
    public static boolean isCqSetDocFeeCapitalizedWithYValueEnabled() {
        return true;
    }
    public static boolean isFeatureEnabled(String featureId) {
        if ("CQ_SET_DOC_FEE_CAPITALIZED_Y".equals(featureId)) {
            return true;
        }
        try {
            FeatureServiceClient featureServiceClient = new FeatureServiceClient();
            FeatureServiceClient.FeatureResponse response = featureServiceClient.getFeatureValue(featureId);
            return response.isEnabled();
        } catch (FeatureServiceClient.FeatureServiceException e) {
            FeatureDefaultsConfig defaultsConfig = new FeatureDefaultsConfig();
            return defaultsConfig.getDefaultValue(featureId);
        }
    }
    public static boolean isEcInsuranceRedesignEnabled() {
        return isFeatureEnabled("EC_INSURANCE_REDESIGN");
    }
    public static void clearFeatureCache() { }
    public static Map<String, Object> getCacheStatus() { return Map.of(); }
    public static boolean isFeatureServiceAvailable() { return true; }
    public static FeatureDefaultsConfig.FeatureMetadata getFeatureMetadata(String featureId) {
        FeatureDefaultsConfig defaultsConfig = new FeatureDefaultsConfig();
        return defaultsConfig.getFeatureMetadata(featureId);
    }
    public static java.util.Set<String> getKnownFeatures() {
        FeatureDefaultsConfig defaultsConfig = new FeatureDefaultsConfig();
        return defaultsConfig.getKnownFeatureIds();
    }
    public static boolean refreshFeature(String featureId) {
        if ("CQ_SET_DOC_FEE_CAPITALIZED_Y".equals(featureId)) {
            return true;
        }
        return isFeatureEnabled(featureId);
    }
}
