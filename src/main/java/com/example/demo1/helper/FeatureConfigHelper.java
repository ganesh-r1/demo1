package com.example.demo1.helper;

import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.HashMap;

@Component
public class FeatureConfigHelper {
    
    public Map<String, Object> buildConfiguration(boolean docFeeCapitalized, boolean insuranceRedesigned) {
        Map<String, Object> config = new HashMap<>();
        
        // Only process CQ_SET_DOC_FEE_CAPITALIZED_Y
        if (docFeeCapitalized) {
            config.put("fee.display.format", "CAPITALIZED_Y");
            config.put("fee.calculation.multiplier", 1.15);
            config.put("fee.precision", 4);
        } else {
            config.put("fee.display.format", "STANDARD");
            config.put("fee.calculation.multiplier", 1.0);
            config.put("fee.precision", 2);
        }
        // Insurance redesign config and keys removed
        return config;
    }
    
    public String determineSystemMode(boolean docFeeEnabled, boolean insuranceEnabled) {
        // Only docFeeEnabled considered
        if (docFeeEnabled) {
            return "DOC_FEE_ENHANCED_MODE";
        }
        return "BASIC_MODE";
    }
    
    public int calculateProcessingPriority(boolean docFeeEnabled, boolean insuranceEnabled, String documentType) {
        int basePriority = 5;
        if (docFeeEnabled && "financial".equals(documentType)) {
            basePriority += 3;
        }
        return Math.min(basePriority, 10);
    }
}
