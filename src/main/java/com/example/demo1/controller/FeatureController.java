package com.example.demo1.controller;

import com.example.demo1.feature.FeatureControlCheckUtil;
import com.example.demo1.helper.FeatureConfigHelper;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Map;

@RestController
public class FeatureController {
    
    private final FeatureConfigHelper configHelper;
    
    public FeatureController(FeatureConfigHelper configHelper) {
        this.configHelper = configHelper;
    }
    
    @GetMapping("/features/status")
    public Map<String, Object> getFeatureStatus() {
        // Get feature states and pass to helper
        boolean docFeeEnabled = FeatureControlCheckUtil.isCqSetDocFeeCapitalizedWithYValueEnabled();
        // REMOVE: boolean insuranceEnabled = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
        
        // REMOVE: Map<String, Object> config = configHelper.buildConfiguration(docFeeEnabled, insuranceEnabled);
        // REMOVE: String systemMode = configHelper.determineSystemMode(docFeeEnabled, insuranceEnabled);
        
        // REMOVE: config.put("system_mode", systemMode);
        // REMOVE: config.put("timestamp", System.currentTimeMillis());
        
        // REMOVE: return config;
        // Only return configuration for docFeeEnabled
        Map<String, Object> config = configHelper.buildConfiguration(docFeeEnabled, false);
        config.put("timestamp", System.currentTimeMillis());
        return config;
    }
    
    @GetMapping("/features/priority")
    public int getProcessingPriority(@RequestParam String documentType) {
        boolean docFeeFlag = FeatureControlCheckUtil.isCqSetDocFeeCapitalizedWithYValueEnabled();
        // REMOVE: boolean insuranceFlag = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
        
        // REMOVE: return configHelper.calculateProcessingPriority(docFeeFlag, insuranceFlag, documentType);
        return configHelper.calculateProcessingPriority(docFeeFlag, false, documentType);
    }
}
