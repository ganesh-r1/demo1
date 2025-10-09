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
        // Removed insuranceRedesignState
        configureDocumentFeeProperties(props, docFeeCapitalizedState);
        // Removed configureInsuranceProperties and configureCombinedFeatureProperties usage
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
    // Removed configureInsuranceProperties and configureCombinedFeatureProperties
    @Bean
    public Map<String, String> featureStatusMap() {
        Map<String, String> statusMap = new HashMap<>();
        boolean docFeeFlag = FeatureControlCheckUtil.isCqSetDocFeeCapitalizedWithYValueEnabled();
        statusMap.put("CQ_SET_DOC_FEE_CAPITALIZED_Y", docFeeFlag ? "ENABLED" : "DISABLED");
        statusMap.put("COMBINED_STATUS", docFeeFlag ? "DOC_FEE_ONLY" : "BASIC_MODE");
        return statusMap;
    }
    // Removed determineCombinedStatus
}
