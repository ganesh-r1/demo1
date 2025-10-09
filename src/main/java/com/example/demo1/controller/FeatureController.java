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
        // Get feature state for doc fee only
        boolean docFeeEnabled = FeatureControlCheckUtil.isCqSetDocFeeCapitalizedWithYValueEnabled();
        // REMOVE: boolean insuranceEnabled = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
        
        // Pass only docFeeEnabled to configuration and system mode
        Map<String, Object> config = configHelper.buildConfiguration(docFeeEnabled, false);
        String systemMode = configHelper.determineSystemMode(docFeeEnabled, false);
        
        config.put("system_mode", systemMode);
        config.put("timestamp", System.currentTimeMillis());
        
        return config;
    }
    
    @GetMapping("/features/priority")
    public int getProcessingPriority(@RequestParam String documentType) {
        boolean docFeeFlag = FeatureControlCheckUtil.isCqSetDocFeeCapitalizedWithYValueEnabled();
        // REMOVE: boolean insuranceFlag = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
        
        // Pass only docFeeFlag to priority logic
        return configHelper.calculateProcessingPriority(docFeeFlag, false, documentType);
    }
}
