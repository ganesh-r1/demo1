package com.example.demo1.helper;

import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.HashMap;

@Component
public class FeatureConfigHelper {
    
    public Map<String, Object> buildConfiguration(boolean docFeeCapitalized, boolean insuranceRedesigned) {
        Map<String, Object> config = new HashMap<>();
        
        if (docFeeCapitalized) {
            config.put("fee.display.format", "CAPITALIZED_Y");
            config.put("fee.calculation.multiplier", 1.15);
            config.put("fee.precision", 4);
        } else {
            config.put("fee.display.format", "STANDARD");
            config.put("fee.calculation.multiplier", 1.0);
            config.put("fee.precision", 2);
        }
        
        if (insuranceRedesigned) {
            // Disable insuranceRedesigned block as feature is removed
            // config.put("insurance.ui.theme", "MODERN_REDESIGN");
            // config.put("insurance.processing.algorithm", "ENHANCED");
            // config.put("insurance.validation.strict", true);
            config.put("insurance.ui.theme", "CLASSIC");
            config.put("insurance.processing.algorithm", "STANDARD");
            config.put("insurance.validation.strict", false);
        } else {
            config.put("insurance.ui.theme", "CLASSIC");
            config.put("insurance.processing.algorithm", "STANDARD");
            config.put("insurance.validation.strict", false);
        }
        
        return config;
    }
    
    public String determineSystemMode(boolean docFeeEnabled, boolean insuranceEnabled) {
        // Only consider docFeeEnabled as insurance feature is removed
        if (docFeeEnabled) {
            return "PARTIAL_FEATURE_MODE";
        }
        return "BASIC_MODE";
    }
    
    public int calculateProcessingPriority(boolean docFeeEnabled, boolean insuranceEnabled, String documentType) {
        int basePriority = 5;
        
        if (docFeeEnabled && "financial".equals(documentType)) {
            basePriority += 3;
        }
        // Remove insuranceEnabled block
        return Math.min(basePriority, 10); // Cap at 10
    }
}
