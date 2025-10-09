package com.example.demo1.controller;

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
        // Feature always enabled: set to true
        boolean docFeeEnabled = true;
        boolean insuranceEnabled = false; // Use actual value or false as fallback
        
        Map<String, Object> config = configHelper.buildConfiguration(docFeeEnabled, insuranceEnabled);
        String systemMode = configHelper.determineSystemMode(docFeeEnabled, insuranceEnabled);
        
        config.put("system_mode", systemMode);
        config.put("timestamp", System.currentTimeMillis());
        
        return config;
    }
    
    @GetMapping("/features/priority")
    public int getProcessingPriority(@RequestParam String documentType) {
        // Feature always enabled: set to true
        boolean docFeeFlag = true;
        boolean insuranceFlag = false; // Use actual value or false
        
        return configHelper.calculateProcessingPriority(docFeeFlag, insuranceFlag, documentType);
    }
}
