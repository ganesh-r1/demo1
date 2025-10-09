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
        // REMOVED: boolean insuranceEnabled = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
        boolean insuranceEnabled = false; // Hardcoded to feature OFF after flag removal
        
        Map<String, Object> config = configHelper.buildConfiguration(docFeeEnabled, insuranceEnabled);
        String systemMode = configHelper.determineSystemMode(docFeeEnabled, insuranceEnabled);
        
        config.put("system_mode", systemMode);
        config.put("timestamp", System.currentTimeMillis());
        
        return config;
    }
    
    @GetMapping("/features/priority")
    public int getProcessingPriority(@RequestParam String documentType) {
        boolean docFeeFlag = FeatureControlCheckUtil.isCqSetDocFeeCapitalizedWithYValueEnabled();
        // REMOVED: boolean insuranceFlag = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
        boolean insuranceFlag = false; // Hardcoded to feature OFF after flag removal
        
        return configHelper.calculateProcessingPriority(docFeeFlag, insuranceFlag, documentType);
    }
}
