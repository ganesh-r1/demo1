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
        
        // Get only insurance feature state
        boolean insuranceRedesignState = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
        
        // Configure properties based on insurance feature only
        configureInsuranceProperties(props, insuranceRedesignState);
        configureCombinedFeatureProperties(props, insuranceRedesignState);
        
        return props;
    }
    
    // Document fee feature config removed
    
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
    
    private void configureCombinedFeatureProperties(Properties props, boolean insuranceEnabled) {
        if (insuranceEnabled) {
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
