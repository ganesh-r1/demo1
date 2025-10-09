package com.example.demo1.config;

import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.HashMap;

@Component
public class FeatureDefaultsConfig {
    
    private static final Map<String, Boolean> DEFAULT_FEATURE_VALUES = new HashMap<>();
    private static final Map<String, FeatureMetadata> FEATURE_METADATA = new HashMap<>();
    
    static {
        // Initialize default feature values
        // DEFAULT_FEATURE_VALUES.put("CQ_SET_DOC_FEE_CAPITALIZED_Y", true); // REMOVE FLAG
        DEFAULT_FEATURE_VALUES.put("EC_INSURANCE_REDESIGN", false);
        
        // Initialize feature metadata
        // FEATURE_METADATA.put("CQ_SET_DOC_FEE_CAPITALIZED_Y", 
        //    new FeatureMetadata(
        //        "CQ_SET_DOC_FEE_CAPITALIZED_Y",
        //        "Enhanced document fee calculation with capitalized Y format",
        //        "BILLING",
        //        "UI_AND_CALCULATION",
        //        true
        //    )
        // ); // REMOVE FLAG
        
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
    
    /**
     * Get default value for a feature
     */
    public boolean getDefaultValue(String featureId) {
        return DEFAULT_FEATURE_VALUES.getOrDefault(featureId, false);
    }
    
    /**
     * Check if feature is known/configured
     */
    public boolean isKnownFeature(String featureId) {
        return DEFAULT_FEATURE_VALUES.containsKey(featureId);
    }
    
    /**
     * Get all default feature values
     */
    public Map<String, Boolean> getAllDefaults() {
        return new HashMap<>(DEFAULT_FEATURE_VALUES);
    }
    
    /**
     * Get feature metadata
     */
    public FeatureMetadata getFeatureMetadata(String featureId) {
        return FEATURE_METADATA.get(featureId);
    }
    
    /**
     * Get all known feature IDs
     */
    public java.util.Set<String> getKnownFeatureIds() {
        return DEFAULT_FEATURE_VALUES.keySet();
    }
    
    /**
     * Add or update a feature default (for testing/configuration)
     */
    public void setDefaultValue(String featureId, boolean defaultValue) {
        DEFAULT_FEATURE_VALUES.put(featureId, defaultValue);
    }
    
    /**
     * Add feature metadata
     */
    public void setFeatureMetadata(String featureId, FeatureMetadata metadata) {
        FEATURE_METADATA.put(featureId, metadata);
    }
    
    /**
     * Feature metadata class
     */
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
