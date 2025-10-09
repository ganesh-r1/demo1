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
        
        boolean insuranceRedesignState = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
        
        // Hard-code document fee feature as always enabled
        configureDocumentFeeProperties(props);
        configureInsuranceProperties(props, insuranceRedesignState);
        configureCombinedFeatureProperties(props, insuranceRedesignState);
        
        return props;
    }
    
    private void configureDocumentFeeProperties(Properties props) {
        props.setProperty("document.fee.format", "CAPITALIZED_Y");
        props.setProperty("document.fee.multiplier", "1.15");
        props.setProperty("document.fee.precision", "4");
        props.setProperty("document.fee.validation.strict", "true");
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
    
    private void configureCombinedFeatureProperties(Properties props, boolean insuranceEnabled) {
        if (insuranceEnabled) {
            props.setProperty("system.mode", "PREMIUM_ENHANCED");
            props.setProperty("processing.priority", "HIGH");
            props.setProperty("feature.compatibility.check", "COMPREHENSIVE");
        } else {
            props.setProperty("system.mode", "PARTIAL_ENHANCED");
            props.setProperty("processing.priority", "MEDIUM");
        }
    }
    
    @Bean
    public Map<String, String> featureStatusMap() {
        Map<String, String> statusMap = new HashMap<>();
        boolean insuranceFlag = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
        statusMap.put("CQ_SET_DOC_FEE_CAPITALIZED_Y", "ENABLED");
        statusMap.put("EC_INSURANCE_REDESIGN", insuranceFlag ? "ENABLED" : "DISABLED");
        statusMap.put("COMBINED_STATUS", insuranceFlag ? "FULL_FEATURE_SET" : "INSURANCE_ONLY");
        return statusMap;
    }
}
