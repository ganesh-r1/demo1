package com.example.demo1.config;

import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.HashMap;

@Component
public class FeatureDefaultsConfig {
    
    private static final Map<String, Boolean> DEFAULT_FEATURE_VALUES = new HashMap<>();
    private static final Map<String, FeatureMetadata> FEATURE_METADATA = new HashMap<>();
    
    static {
        // No longer store CQ_SET_DOC_FEE_CAPITALIZED_Y; always enabled
        DEFAULT_FEATURE_VALUES.put("EC_INSURANCE_REDESIGN", false);
        
        FEATURE_METADATA.put("EC_INSURANCE_REDESIGN", 
            new FeatureMetadata(
                "EC_INSURANCE_REDESIGN",
                "Redesigned insurance experience with AI-powered features",
                "UI_ENHANCEMENT",
                "FULL_UI_REDESIGN",
                false
            )
        );
    }
    
    public boolean getDefaultValue(String featureId) {
        if ("CQ_SET_DOC_FEE_CAPITALIZED_Y".equals(featureId)) {
            return true;
        }
        return DEFAULT_FEATURE_VALUES.getOrDefault(featureId, false);
    }
    
    public boolean isKnownFeature(String featureId) {
        if ("CQ_SET_DOC_FEE_CAPITALIZED_Y".equals(featureId)) {
            return false;
        }
        return DEFAULT_FEATURE_VALUES.containsKey(featureId);
    }
    
    public Map<String, Boolean> getAllDefaults() {
        return new HashMap<>(DEFAULT_FEATURE_VALUES);
    }
    
    public FeatureMetadata getFeatureMetadata(String featureId) {
        if ("CQ_SET_DOC_FEE_CAPITALIZED_Y".equals(featureId)) {
            return null;
        }
        return FEATURE_METADATA.get(featureId);
    }
    
    public java.util.Set<String> getKnownFeatureIds() {
        return DEFAULT_FEATURE_VALUES.keySet();
    }
    
    public void setDefaultValue(String featureId, boolean defaultValue) {
        if (!"CQ_SET_DOC_FEE_CAPITALIZED_Y".equals(featureId)) {
            DEFAULT_FEATURE_VALUES.put(featureId, defaultValue);
        }
    }
    
    public void setFeatureMetadata(String featureId, FeatureMetadata metadata) {
        if (!"CQ_SET_DOC_FEE_CAPITALIZED_Y".equals(featureId)) {
            FEATURE_METADATA.put(featureId, metadata);
        }
    }
    
    public static class FeatureMetadata {
        private final String featureId;
        private final String description;
        private final String category;
        private final String impact;
        private final boolean defaultValue;
        
        public FeatureMetadata(String featureId, String description, String category, 
                             String impact, boolean defaultValue) {
            this.featureId = featureId;
            this.description = description;
            this.category = category;
            this.impact = impact;
            this.defaultValue = defaultValue;
        }
        
        public String getFeatureId() { return featureId; }
        public String getDescription() { return description; }
        public String getCategory() { return category; }
        public String getImpact() { return impact; }
        public boolean getDefaultValue() { return defaultValue; }
        
        @Override
        public String toString() {
            return String.format("FeatureMetadata{featureId='%s', description='%s', category='%s', impact='%s', defaultValue=%s}", 
                    featureId, description, category, impact, defaultValue);
        }
    }
}
