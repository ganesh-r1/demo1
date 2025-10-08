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
        // Only docFeeCapitalizedEnabled
        boolean docFeeCapitalizedEnabled = FeatureControlCheckUtil.isCqSetDocFeeCapitalizedWithYValueEnabled();
        // REMOVE insuranceRedesignEnabled
        response.put("cq_set_doc_fee_capitalized_y_status", docFeeCapitalizedEnabled);
        // REMOVE ec_insurance_redesign_status
        response.putAll(buildFeatureConfiguration(docFeeCapitalizedEnabled, false));
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("server_time", System.currentTimeMillis());
        response.put("api_version", "1.0");
        return response;
    }
    
    private Map<String, Object> buildFeatureConfiguration(boolean docFeeEnabled, boolean insuranceEnabled) {
        // insuranceEnabled should always be false, OR remove second param, OR remove code that depends on insuranceEnabled
        return Collections.emptyMap();
    }
    
    private String determineSystemMode(boolean docFeeEnabled, boolean insuranceEnabled) {
        // Remove branch with insuranceEnabled, return only docFee states
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
        boolean docFeeEnabled = FeatureControlCheckUtil.isCqSetDocFeeCapitalizedWithYValueEnabled();
        // REMOVE insuranceEnabled
        Map<String, Object> docFeeDetails = new HashMap<>();
        docFeeDetails.put("enabled", docFeeEnabled);
        docFeeDetails.put("feature_name", "CQ_SET_DOC_FEE_CAPITALIZED_Y");
        docFeeDetails.put("description", "Enhanced document fee calculation with capitalized Y format");
        docFeeDetails.put("impact_areas", new String[]{"billing", "document_processing", "fee_calculation"});
        docFeeDetails.put("ui_changes", docFeeEnabled ? "Enhanced fee display and calculation UI" : "Standard fee UI");
        response.put("cq_set_doc_fee_capitalized_y", docFeeDetails);
        // REMOVE insuranceDetails and all references
        // REMOVE compatibility checks for insuranceEnabled
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("check_performed_at", System.currentTimeMillis());
        return response;
    }
    
    private boolean areFeatureCompatible(boolean docFeeEnabled, boolean insuranceEnabled) {
        // REMOVE insuranceEnabled param/computation
        return true;
    }
    
    private String calculatePerformanceImpact(boolean docFeeEnabled, boolean insuranceEnabled) {
        // REMOVE insuranceEnabled param/computation
        return docFeeEnabled ? "LOW" : "MINIMAL";
    }
    
    @GetMapping("/status/health")
    public Map<String, Object> getFeatureHealthStatus() {
        Map<String, Object> health = new HashMap<>();
        try {
            boolean docFeeCheck = FeatureControlCheckUtil.isCqSetDocFeeCapitalizedWithYValueEnabled();
            // REMOVE insuranceCheck
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
