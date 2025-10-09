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
        
        // Only CQ_SET_DOC_FEE_CAPITALIZED_Y status supported
        boolean docFeeCapitalizedEnabled = FeatureControlCheckUtil.isCqSetDocFeeCapitalizedWithYValueEnabled();
        
        response.put("cq_set_doc_fee_capitalized_y_status", docFeeCapitalizedEnabled);
        // Removed ec_insurance_redesign_status
        
        // Add derived configuration based on feature states
        response.putAll(buildFeatureConfiguration(docFeeCapitalizedEnabled, false));
        
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
        
        // Only doc fee feature retained
        boolean docFeeEnabled = FeatureControlCheckUtil.isCqSetDocFeeCapitalizedWithYValueEnabled();
        
        Map<String, Object> docFeeDetails = new HashMap<>();
        docFeeDetails.put("enabled", docFeeEnabled);
        docFeeDetails.put("feature_name", "CQ_SET_DOC_FEE_CAPITALIZED_Y");
        docFeeDetails.put("description", "Enhanced document fee calculation with capitalized Y format");
        docFeeDetails.put("impact_areas", new String[]{"billing", "document_processing", "fee_calculation"});
        docFeeDetails.put("ui_changes", docFeeEnabled ? "Enhanced fee display and calculation UI" : "Standard fee UI");
        
        response.put("cq_set_doc_fee_capitalized_y", docFeeDetails);
        // Removed ec_insurance_redesign details
        
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
            boolean docFeeCheck = FeatureControlCheckUtil.isCqSetDocFeeCapitalizedWithYValueEnabled();
            
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
