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
        // Use insurance feature only
        boolean insuranceEnabled = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
        
        Map<String, Object> config = configHelper.buildConfiguration(false, insuranceEnabled);
        String systemMode = configHelper.determineSystemMode(false, insuranceEnabled);
        
        config.put("system_mode", systemMode);
        config.put("timestamp", System.currentTimeMillis());
        
        return config;
    }
    
    @GetMapping("/features/priority")
    public int getProcessingPriority(@RequestParam String documentType) {
        boolean insuranceFlag = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
        
        return configHelper.calculateProcessingPriority(false, insuranceFlag, documentType);
    }
}
