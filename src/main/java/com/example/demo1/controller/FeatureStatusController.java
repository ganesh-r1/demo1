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
        boolean insuranceRedesignEnabled = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
        
        // Add individual feature statuses
        response.put("cq_set_doc_fee_capitalized_y_status", docFeeCapitalizedEnabled);
        response.put("ec_insurance_redesign_status", insuranceRedesignEnabled);
        
        // Add derived configuration based on feature states
        response.putAll(buildFeatureConfiguration(docFeeCapitalizedEnabled, insuranceRedesignEnabled));
        
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
        
        if (insuranceEnabled) {
            uiConfig.put("insurance_theme", "MODERN_REDESIGN");
            uiConfig.put("ai_features_enabled", true);
            uiConfig.put("enhanced_claims_ui", true);
            uiConfig.put("smart_search_enabled", true);
            uiConfig.put("photo_claims_enabled", true);
        } else {
            uiConfig.put("insurance_theme", "CLASSIC");
            uiConfig.put("ai_features_enabled", false);
            uiConfig.put("enhanced_claims_ui", false);
            uiConfig.put("smart_search_enabled", false);
            uiConfig.put("photo_claims_enabled", false);
        }
        
        config.put("ui_config", uiConfig);
        
        // Feature capabilities
        Map<String, Object> capabilities = new HashMap<>();
        capabilities.put("document_processing", docFeeEnabled ? "ENHANCED" : "STANDARD");
        capabilities.put("insurance_processing", insuranceEnabled ? "AI_POWERED" : "TRADITIONAL");
        capabilities.put("claims_processing", insuranceEnabled ? "DIGITAL_FIRST" : "MANUAL");
        capabilities.put("risk_assessment", insuranceEnabled ? "AI_ENABLED" : "BASIC");
        
        config.put("capabilities", capabilities);
        
        // Processing configuration
        Map<String, Object> processing = new HashMap<>();
        processing.put("priority_level", determinePriorityLevel(docFeeEnabled, insuranceEnabled));
        processing.put("enhanced_validation", docFeeEnabled || insuranceEnabled);
        processing.put("real_time_updates", insuranceEnabled);
        processing.put("batch_processing", !insuranceEnabled);
        
        config.put("processing", processing);
        
        return config;
    }
    
    private String determineSystemMode(boolean docFeeEnabled, boolean insuranceEnabled) {
        if (docFeeEnabled && insuranceEnabled) {
            return "PREMIUM_ENHANCED";
        } else if (docFeeEnabled) {
            return "DOC_FEE_ENHANCED";
        } else if (insuranceEnabled) {
            return "INSURANCE_ENHANCED";
        }
        return "STANDARD";
    }
    
    private String determinePriorityLevel(boolean docFeeEnabled, boolean insuranceEnabled) {
        if (docFeeEnabled && insuranceEnabled) {
            return "HIGH";
        } else if (docFeeEnabled || insuranceEnabled) {
            return "MEDIUM";
        }
        return "NORMAL";
    }
    
    @GetMapping("/status/detailed")
    public Map<String, Object> getDetailedFeatureStatus() {
        Map<String, Object> response = new HashMap<>();
        
        // Get feature states
        boolean docFeeEnabled = FeatureControlCheckUtil.isCqSetDocFeeCapitalizedWithYValueEnabled();
        boolean insuranceEnabled = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
        
        // Detailed feature information
        Map<String, Object> docFeeDetails = new HashMap<>();
        docFeeDetails.put("enabled", docFeeEnabled);
        docFeeDetails.put("feature_name", "CQ_SET_DOC_FEE_CAPITALIZED_Y");
        docFeeDetails.put("description", "Enhanced document fee calculation with capitalized Y format");
        docFeeDetails.put("impact_areas", new String[]{"billing", "document_processing", "fee_calculation"});
        docFeeDetails.put("ui_changes", docFeeEnabled ? "Enhanced fee display and calculation UI" : "Standard fee UI");
        
        Map<String, Object> insuranceDetails = new HashMap<>();
        insuranceDetails.put("enabled", insuranceEnabled);
        insuranceDetails.put("feature_name", "EC_INSURANCE_REDESIGN");
        insuranceDetails.put("description", "Redesigned insurance experience with AI-powered features");
        insuranceDetails.put("impact_areas", new String[]{"claims", "ui_theme", "ai_features", "risk_assessment"});
        insuranceDetails.put("ui_changes", insuranceEnabled ? "Modern redesigned insurance interface" : "Classic insurance UI");
        
        response.put("cq_set_doc_fee_capitalized_y", docFeeDetails);
        response.put("ec_insurance_redesign", insuranceDetails);
        
        // System compatibility
        Map<String, Object> compatibility = new HashMap<>();
        compatibility.put("features_compatible", areFeatureCompatible(docFeeEnabled, insuranceEnabled));
        compatibility.put("requires_database_upgrade", docFeeEnabled && insuranceEnabled);
        compatibility.put("performance_impact", calculatePerformanceImpact(docFeeEnabled, insuranceEnabled));
        
        response.put("compatibility", compatibility);
        
        // Add metadata
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("check_performed_at", System.currentTimeMillis());
        
        return response;
    }
    
    private boolean areFeatureCompatible(boolean docFeeEnabled, boolean insuranceEnabled) {
        // Both features are compatible with each other
        return true;
    }
    
    private String calculatePerformanceImpact(boolean docFeeEnabled, boolean insuranceEnabled) {
        if (docFeeEnabled && insuranceEnabled) {
            return "MODERATE"; // Both features together have moderate impact
        } else if (docFeeEnabled || insuranceEnabled) {
            return "LOW"; // Single feature has low impact
        }
        return "MINIMAL"; // No enhanced features
    }
    
    @GetMapping("/status/health")
    public Map<String, Object> getFeatureHealthStatus() {
        Map<String, Object> health = new HashMap<>();
        
        try {
            // Check if feature control utility is accessible
            boolean docFeeCheck = FeatureControlCheckUtil.isCqSetDocFeeCapitalizedWithYValueEnabled();
            boolean insuranceCheck = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
            
            health.put("feature_service_status", "HEALTHY");
            health.put("doc_fee_check_successful", true);
            health.put("insurance_check_successful", true);
            health.put("last_check_time", LocalDateTime.now().toString());
            
        } catch (Exception e) {
            health.put("feature_service_status", "UNHEALTHY");
            health.put("error_message", e.getMessage());
            health.put("doc_fee_check_successful", false);
            health.put("insurance_check_successful", false);
        }
        
        return health;
    }
}
