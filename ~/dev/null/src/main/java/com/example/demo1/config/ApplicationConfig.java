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
        
        boolean docFeeCapitalizedState = FeatureControlCheckUtil.isCqSetDocFeeCapitalizedWithYValueEnabled();
        
        configureDocumentFeeProperties(props, docFeeCapitalizedState);
        configureInsuranceProperties(props);
        configureCombinedFeatureProperties(props, docFeeCapitalizedState);
        
        return props;
    }
    
    private void configureDocumentFeeProperties(Properties props, boolean featureEnabled) {
        if (featureEnabled) {
            props.setProperty("document.fee.format", "CAPITALIZED_Y");
            props.setProperty("document.fee.multiplier", "1.15");
            props.setProperty("document.fee.precision", "4");
            props.setProperty("document.fee.validation.strict", "true");
        } else {
            props.setProperty("document.fee.format", "STANDARD");
            props.setProperty("document.fee.multiplier", "1.0");
            props.setProperty("document.fee.precision", "2");
        }
    }
    
    private void configureInsuranceProperties(Properties props) {
        props.setProperty("insurance.ui.theme", "MODERN_REDESIGN");
        props.setProperty("insurance.processing.enhanced", "true");
        props.setProperty("insurance.validation.ai.enabled", "true");
        props.setProperty("insurance.workflow.digital", "true");
    }
    
    private void configureCombinedFeatureProperties(Properties props, boolean docFeeEnabled) {
        if (docFeeEnabled) {
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
        
        boolean docFeeFlag = FeatureControlCheckUtil.isCqSetDocFeeCapitalizedWithYValueEnabled();
        
        statusMap.put("CQ_SET_DOC_FEE_CAPITALIZED_Y", docFeeFlag ? "ENABLED" : "DISABLED");
        statusMap.put("EC_INSURANCE_REDESIGN", "ENABLED");
        statusMap.put("COMBINED_STATUS", determineCombinedStatus(docFeeFlag));
        
        return statusMap;
    }
    
    private String determineCombinedStatus(boolean docFee) {
        if (docFee) {
            return "DOC_FEE_ONLY";
        }
        return "BASIC_MODE";
    }
}