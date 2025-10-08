package com.example.demo1.helper;

import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.HashMap;

@Component
public class FeatureConfigHelper {
    public Map<String, Object> buildConfiguration(boolean docFeeCapitalized, boolean insuranceRedesigned) {
        Map<String, Object> config = new HashMap<>();
        // docFeeCapitalized is always true (after feature flag removal)
        config.put("fee.display.format", "CAPITALIZED_Y");
        config.put("fee.calculation.multiplier", 1.15);
        config.put("fee.precision", 4);
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
    public String determineSystemMode(boolean docFeeEnabled, boolean insuranceEnabled) {
        // docFeeEnabled is always true
        if (insuranceEnabled) {
            return "FULL_FEATURE_MODE";
        }
        return "PARTIAL_FEATURE_MODE";
    }
    public int calculateProcessingPriority(boolean docFeeEnabled, boolean insuranceEnabled, String documentType) {
        // docFeeEnabled is always true
        int basePriority = 5;
        if ("financial".equals(documentType)) {
            basePriority += 3;
        }
        if (insuranceEnabled && "insurance".equals(documentType)) {
            basePriority += 2;
        }
        return Math.min(basePriority, 10);
    }
}
