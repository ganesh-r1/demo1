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
        // MODIFIED: insuranceEnabled set to false anywhere this method called
        return Collections.emptyMap();
    }
    
    private String determineSystemMode(boolean docFeeEnabled, boolean insuranceEnabled) {
        // MODIFIED: insuranceEnabled will always be false
        if (docFeeEnabled) {
            return "DOC_FEE_ENHANCED";
        }
        return "STANDARD";
    }
    
    private String determinePriorityLevel(boolean docFeeEnabled, boolean insuranceEnabled) {
        // MODIFIED: insuranceEnabled will always be false
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
        // REMOVED: boolean insuranceEnabled = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
        
        // Detailed feature information
        Map<String, Object> docFeeDetails = new HashMap<>();
        docFeeDetails.put("enabled", docFeeEnabled);
        docFeeDetails.put("feature_name", "CQ_SET_DOC_FEE_CAPITALIZED_Y");
        docFeeDetails.put("description", "Enhanced document fee calculation with capitalized Y format");
        docFeeDetails.put("impact_areas", new String[]{"billing", "document_processing", "fee_calculation"});
        docFeeDetails.put("ui_changes", docFeeEnabled ? "Enhanced fee display and calculation UI" : "Standard fee UI");
        
        // REMOVED: insuranceDetails, logic, and put of "ec_insurance_redesign"
        response.put("cq_set_doc_fee_capitalized_y", docFeeDetails);
        // REMOVED: response.put("ec_insurance_redesign", insuranceDetails);
        
        // System compatibility
        Map<String, Object> compatibility = new HashMap<>();
        compatibility.put("features_compatible", true); // Only one feature now
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
            // REMOVED: health.put("insurance_check_successful", true);
            health.put("last_check_time", LocalDateTime.now().toString());
            
        } catch (Exception e) {
            health.put("feature_service_status", "UNHEALTHY");
            health.put("error_message", e.getMessage());
            health.put("doc_fee_check_successful", false);
            // REMOVED: health.put("insurance_check_successful", false);
        }
        
        return health;
    }
}
