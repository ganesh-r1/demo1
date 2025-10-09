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
        // Only process EC_INSURANCE_REDESIGN
        boolean insuranceEnabled = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
        
        // Pass 'true' for docFeeEnabled to config helper, since feature always enabled
        Map<String, Object> config = configHelper.buildConfiguration(true, insuranceEnabled);
        String systemMode = configHelper.determineSystemMode(true, insuranceEnabled);
        
        config.put("system_mode", systemMode);
        config.put("timestamp", System.currentTimeMillis());
        
        return config;
    }
    
    @GetMapping("/features/priority")
    public int getProcessingPriority(@RequestParam String documentType) {
        boolean insuranceFlag = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
        // docFeeFlag always 'true' after removal
        return configHelper.calculateProcessingPriority(true, insuranceFlag, documentType);
    }
}
