package com.example.demo1.helper;

import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.HashMap;

@Component
public class FeatureConfigHelper {
    
    public Map<String, Object> buildConfiguration(boolean docFeeCapitalizedIgnored, boolean insuranceRedesigned) {
        Map<String, Object> config = new HashMap<>();
        
        // Document fee config always standard, feature removed
        config.put("fee.display.format", "STANDARD");
        config.put("fee.calculation.multiplier", 1.0);
        config.put("fee.precision", 2);
        
        if (insuranceRedesigned) {
            config.put("insurance.ui.theme", "MODERN_REDESIGN");
            config.put("insurance.processing.algorithm", "ENHANCED");
            config.put("insurance.validation.strict", true);
        } else {
            config.put("insurance.ui.theme", "CLASSIC");
            config.put("insurance.processing.algorithm", "STANDARD");
            config.put("insurance.validation.strict", false);
        }
        
        return config;
    }
    
    public String determineSystemMode(boolean docFeeEnabledIgnored, boolean insuranceEnabled) {
        if (insuranceEnabled) {
            return "PARTIAL_FEATURE_MODE";
        }
        return "BASIC_MODE";
    }
    
    public int calculateProcessingPriority(boolean docFeeEnabledIgnored, boolean insuranceEnabled, String documentType) {
        int basePriority = 5;
        
        if (insuranceEnabled && "insurance".equals(documentType)) {
            basePriority += 2;
        }
        
        return Math.min(basePriority, 10);
    }
}
