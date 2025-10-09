package com.example.demo1.config;

import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.HashMap;

@Component
public class FeatureDefaultsConfig {
    
    private static final Map<String, Boolean> DEFAULT_FEATURE_VALUES = new HashMap<>();
    private static final Map<String, FeatureMetadata> FEATURE_METADATA = new HashMap<>();
    
    static {
        DEFAULT_FEATURE_VALUES.put("CQ_SET_DOC_FEE_CAPITALIZED_Y", true);
        FEATURE_METADATA.put("CQ_SET_DOC_FEE_CAPITALIZED_Y", 
            new FeatureMetadata(
                "CQ_SET_DOC_FEE_CAPITALIZED_Y",
                "Enhanced document fee calculation with capitalized Y format",
                "BILLING",
                "UI_AND_CALCULATION",
                true
            )
        );
    }
    
    public boolean getDefaultValue(String featureId) {
        return DEFAULT_FEATURE_VALUES.getOrDefault(featureId, false);
    }
    
    public boolean isKnownFeature(String featureId) {
        return DEFAULT_FEATURE_VALUES.containsKey(featureId);
    }
    
    public Map<String, Boolean> getAllDefaults() {
        return new HashMap<>(DEFAULT_FEATURE_VALUES);
    }
    
    public FeatureMetadata getFeatureMetadata(String featureId) {
        return FEATURE_METADATA.get(featureId);
    }
    
    public java.util.Set<String> getKnownFeatureIds() {
        return DEFAULT_FEATURE_VALUES.keySet();
    }
    
    public void setDefaultValue(String featureId, boolean defaultValue) {
        DEFAULT_FEATURE_VALUES.put(featureId, defaultValue);
    }
    
    public void setFeatureMetadata(String featureId, FeatureMetadata metadata) {
        FEATURE_METADATA.put(featureId, metadata);
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
