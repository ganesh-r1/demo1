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
        // Remove docFeeCapitalizedState logic
        boolean insuranceRedesignState = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
        // Remove references to docFeeCapitalizedState
        configureInsuranceProperties(props, insuranceRedesignState);
        // Remove configureDocumentFeeProperties and configureCombinedFeatureProperties
        return props;
    }
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
    @Bean
    public Map<String, String> featureStatusMap() {
        Map<String, String> statusMap = new HashMap<>();
        boolean insuranceFlag = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
        statusMap.put("EC_INSURANCE_REDESIGN", insuranceFlag ? "ENABLED" : "DISABLED");
        statusMap.put("COMBINED_STATUS", insuranceFlag ? "INSURANCE_ONLY" : "BASIC_MODE");
        return statusMap;
    }
    private String determineCombinedStatus(boolean insurance) {
        if (insurance) {
            return "INSURANCE_ONLY";
        }
        return "BASIC_MODE";
    }
}
