package com.example.demo1.config;

import com.example.demo1.feature.FeatureControlCheckUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Properties;
import java.util.Map;
import java.util.HashMap;

@Configuration
public class ApplicationConfig {
    
    @Bean
    public Properties applicationProperties() {
        Properties props = new Properties();
        // REMOVE: docFeeCapitalizedState logic, only insurance logic retained
        boolean insuranceRedesignState = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
        configureInsuranceProperties(props, insuranceRedesignState);
        configureCombinedFeatureProperties(props, false, insuranceRedesignState);
        return props;
    }
    
    // REMOVE: DocumentFeeProperties method
    
    private void configureInsuranceProperties(Properties props, boolean featureEnabled) {
        if (featureEnabled) {
            props.setProperty("insurance.ui.theme", "MODERN_REDESIGN");
            props.setProperty("insurance.processing.enhanced", "true");
            props.setProperty("insurance.validation.ai.enabled", "true");
            props.setProperty("insurance.workflow.digital", "true");
        } else {
            props.setProperty("insurance.ui.theme", "CLASSIC");
            props.setProperty("insurance.processing.enhanced", "false");
        }
    }
    
    private void configureCombinedFeatureProperties(Properties props, boolean docFeeEnabled, boolean insuranceEnabled) {
        if (docFeeEnabled && insuranceEnabled) {
            // REMOVE: PREMIUM_ENHANCED logic
        } else if (docFeeEnabled || insuranceEnabled) {
            props.setProperty("system.mode", "PARTIAL_ENHANCED");
            props.setProperty("processing.priority", "MEDIUM");
        } else {
            props.setProperty("system.mode", "STANDARD");
            props.setProperty("processing.priority", "NORMAL");
        }
    }
    
    @Bean
    public Map<String, String> featureStatusMap() {
        Map<String, String> statusMap = new HashMap<>();
        // REMOVE: CQ_SET_DOC_FEE_CAPITALIZED_Y status
        boolean insuranceFlag = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
        statusMap.put("EC_INSURANCE_REDESIGN", insuranceFlag ? "ENABLED" : "DISABLED");
        statusMap.put("COMBINED_STATUS", determineCombinedStatus(false, insuranceFlag));
        return statusMap;
    }
    
    private String determineCombinedStatus(boolean docFee, boolean insurance) {
        if (docFee && insurance) {
            return "FULL_FEATURE_SET";
        } else if (docFee) {
            return "DOC_FEE_ONLY";
        } else if (insurance) {
            return "INSURANCE_ONLY";
        }
        return "BASIC_MODE";
    }
}
