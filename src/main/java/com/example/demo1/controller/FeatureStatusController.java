package com.example.demo1.controller;

import com.example.demo1.feature.FeatureControlCheckUtil;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Collections;
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
        boolean insuranceRedesignEnabled = false;
        
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
        return Collections.emptyMap();
    }
    
    private String determineSystemMode(boolean docFeeEnabled, boolean insuranceEnabled) {
        // insuranceEnabled always false now
        if (docFeeEnabled) {
            return "DOC_FEE_ENHANCED";
        }
        return "STANDARD";
    }
    
    private String determinePriorityLevel(boolean docFeeEnabled, boolean insuranceEnabled) {
        // insuranceEnabled always false now
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
        boolean insuranceEnabled = false;
        
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
        insuranceDetails.put("ui_changes", "Classic insurance UI");
        
        response.put("cq_set_doc_fee_capitalized_y", docFeeDetails);
        response.put("ec_insurance_redesign", insuranceDetails);
        
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
    
    private boolean areFeatureCompatible(boolean docFeeEnabled, boolean insuranceEnabled) {
        return true;
    }
    
    private String calculatePerformanceImpact(boolean docFeeEnabled, boolean insuranceEnabled) {
        // insuranceEnabled always false
        return docFeeEnabled ? "LOW" : "MINIMAL";
    }
    
    @GetMapping("/status/health")
    public Map<String, Object> getFeatureHealthStatus() {
        Map<String, Object> health = new HashMap<>();
        
        try {
            // Check if feature control utility is accessible
            boolean docFeeCheck = FeatureControlCheckUtil.isCqSetDocFeeCapitalizedWithYValueEnabled();
            boolean insuranceCheck = false;
            
            health.put("feature_service_status", "HEALTHY");
            health.put("doc_fee_check_successful", true);
            health.put("insurance_check_successful", false);
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
