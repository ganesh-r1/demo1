package com.example.demo1.controller;

import com.example.demo1.feature.FeatureControlCheckUtil;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/features")
@CrossOrigin(origins = "*")
public class FeatureStatusController {
    
    @GetMapping("/status")
    public Map<String, Object> getFeatureStatus() {
        Map<String, Object> response = new HashMap<>();
        
        // Get current feature flag states
        boolean docFeeCapitalizedEnabled = FeatureControlCheckUtil.isCqSetDocFeeCapitalizedWithYValueEnabled();
        // REMOVED: boolean insuranceRedesignEnabled = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
        
        // Add individual feature statuses
        response.put("cq_set_doc_fee_capitalized_y_status", docFeeCapitalizedEnabled);
        // REMOVED: response.put("ec_insurance_redesign_status", insuranceRedesignEnabled);
        
        // Add derived configuration based on feature states
        response.putAll(buildFeatureConfiguration(docFeeCapitalizedEnabled, false));
        
        // Add metadata
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("server_time", System.currentTimeMillis());
        response.put("api_version", "1.0");
        
        return response;
    }
    
    private Map<String, Object> buildFeatureConfiguration(boolean docFeeEnabled, boolean insuranceEnabled) {
        Map<String, Object> config = new HashMap<>();
        
        // System mode configuration
        String systemMode = determineSystemMode(docFeeEnabled, insuranceEnabled);
        config.put("system_mode", systemMode);
        
        // UI configuration
        Map<String, Object> uiConfig = new HashMap<>();
        if (docFeeEnabled) {
            uiConfig.put("fee_display_format", "CAPITALIZED_Y");
            uiConfig.put("fee_calculation_precision", 4);
            uiConfig.put("enhanced_fee_ui", true);
        } else {
            uiConfig.put("fee_display_format", "STANDARD");
            uiConfig.put("fee_calculation_precision", 2);
            uiConfig.put("enhanced_fee_ui", false);
        }
        // REMOVED: insuranceEnabled blocks
        config.put("ui_config", uiConfig);
        // Feature capabilities
        Map<String, Object> capabilities = new HashMap<>();
        capabilities.put("document_processing", docFeeEnabled ? "ENHANCED" : "STANDARD");
        // REMOVED: capabilities for insurance
        config.put("capabilities", capabilities);
        // Processing configuration
        Map<String, Object> processing = new HashMap<>();
        processing.put("priority_level", determinePriorityLevel(docFeeEnabled, insuranceEnabled));
        processing.put("enhanced_validation", docFeeEnabled);
        // REMOVED: processing.put("real_time_updates", insuranceEnabled);
        // REMOVED: processing.put("batch_processing", !insuranceEnabled);
        config.put("processing", processing);
        return config;
    }
    
    private String determineSystemMode(boolean docFeeEnabled, boolean insuranceEnabled) {
        if (docFeeEnabled) {
            return "DOC_FEE_ENHANCED";
        }
        return "STANDARD";
    }
    
    private String determinePriorityLevel(boolean docFeeEnabled, boolean insuranceEnabled) {
        if (docFeeEnabled) {
            return "MEDIUM";
        }
        return "NORMAL";
    }
    
    @GetMapping("/status/detailed")
    public Map<String, Object> getDetailedFeatureStatus() {
        Map<String, Object> response = new HashMap<>();
        
        // Get feature states
        boolean docFeeEnabled = FeatureControlCheckUtil.isCqSetDocFeeCapitalizedWithYValueEnabled();
        // REMOVED: boolean insuranceEnabled = ...

        // Detailed feature information
        Map<String, Object> docFeeDetails = new HashMap<>();
        docFeeDetails.put("enabled", docFeeEnabled);
        docFeeDetails.put("feature_name", "CQ_SET_DOC_FEE_CAPITALIZED_Y");
        docFeeDetails.put("description", "Enhanced document fee calculation with capitalized Y format");
        docFeeDetails.put("impact_areas", new String[]{"billing", "document_processing", "fee_calculation"});
        docFeeDetails.put("ui_changes", docFeeEnabled ? "Enhanced fee display and calculation UI" : "Standard fee UI");
        // REMOVED: insuranceDetails
        response.put("cq_set_doc_fee_capitalized_y", docFeeDetails);
        // REMOVED: response.put("ec_insurance_redesign", insuranceDetails);
        // System compatibility
        Map<String, Object> compatibility = new HashMap<>();
        compatibility.put("features_compatible", true);
        compatibility.put("requires_database_upgrade", false);
        compatibility.put("performance_impact", docFeeEnabled ? "LOW" : "MINIMAL");
        response.put("compatibility", compatibility);
        // Add metadata
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("check_performed_at", System.currentTimeMillis());
        return response;
    }
    
    @GetMapping("/status/health")
    public Map<String, Object> getFeatureHealthStatus() {
        Map<String, Object> health = new HashMap<>();
        
        try {
            // Check if feature control utility is accessible
            boolean docFeeCheck = FeatureControlCheckUtil.isCqSetDocFeeCapitalizedWithYValueEnabled();
            // REMOVED: boolean insuranceCheck = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
            
            health.put("feature_service_status", "HEALTHY");
            health.put("doc_fee_check_successful", true);
            health.put("last_check_time", LocalDateTime.now().toString());
            
        } catch (Exception e) {
            health.put("feature_service_status", "UNHEALTHY");
            health.put("error_message", e.getMessage());
            health.put("doc_fee_check_successful", false);
        }
        
        return health;
    }
}
