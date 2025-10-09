package com.example.demo1.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "*")
public class FeatureTestController {
    
    // Simulated feature flag storage for testing
    private static final Map<String, Boolean> testFeatureFlags = new HashMap<>();
    
    static {
        // Initialize with default test values
        // CQ_SET_DOC_FEE_CAPITALIZED_Y is always enabled in code—not toggleable
        testFeatureFlags.put("EC_INSURANCE_REDESIGN", false);
    }
    
    @GetMapping("/feature")
    public Map<String, Object> getFeatureValue(@RequestParam String featureId) {
        Map<String, Object> response = new HashMap<>();
        
        boolean enabled;
        if ("CQ_SET_DOC_FEE_CAPITALIZED_Y".equals(featureId)) {
            enabled = true;
        } else {
            enabled = testFeatureFlags.getOrDefault(featureId, false);
        }
        
        response.put("featureId", featureId);
        response.put("enabled", enabled);
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("source", "TEST_ENDPOINT");
        
        // Add metadata about the feature
        response.put("metadata", getFeatureMetadata(featureId, enabled));
        
        return response;
    }
    
    @GetMapping("/feature/toggle")
    public Map<String, Object> toggleFeature(@RequestParam String featureId) {
        Map<String, Object> response = new HashMap<>();
        
        boolean previousValue;
        boolean newValue;
        if ("CQ_SET_DOC_FEE_CAPITALIZED_Y".equals(featureId)) {
            previousValue = true;
            newValue = true;
            // Cannot toggle; always enabled
        } else {
            previousValue = testFeatureFlags.getOrDefault(featureId, false);
            newValue = !previousValue;
            testFeatureFlags.put(featureId, newValue);
        }
        
        response.put("featureId", featureId);
        response.put("previousValue", previousValue);
        response.put("newValue", newValue);
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("action", "TOGGLED");
        
        return response;
    }
    
    @GetMapping("/feature/set")
    public Map<String, Object> setFeature(@RequestParam String featureId, 
                                        @RequestParam boolean enabled) {
        Map<String, Object> response = new HashMap<>();
        boolean previousValue;
        if ("CQ_SET_DOC_FEE_CAPITALIZED_Y".equals(featureId)) {
            previousValue = true;
            // Cannot set; always enabled
        } else {
            previousValue = testFeatureFlags.getOrDefault(featureId, false);
            testFeatureFlags.put(featureId, enabled);
        }
        
        response.put("featureId", featureId);
        response.put("previousValue", previousValue);
        response.put("newValue", "CQ_SET_DOC_FEE_CAPITALIZED_Y".equals(featureId) ? true : enabled);
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("action", "SET");
        
        return response;
    }
    
    @GetMapping("/feature/all")
    public Map<String, Object> getAllFeatures() {
        Map<String, Object> response = new HashMap<>();
        Map<String, Boolean> features = new HashMap<>(testFeatureFlags);
        features.put("CQ_SET_DOC_FEE_CAPITALIZED_Y", true); // Always enabled
        response.put("features", features);
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("totalFeatures", features.size());
        
        return response;
    }
    
    @GetMapping("/feature/reset")
    public Map<String, Object> resetFeatures() {
        Map<String, Object> response = new HashMap<>();
        
        // Store previous state
        Map<String, Boolean> previousState = new HashMap<>(testFeatureFlags);
        
        // Reset to defaults (CQ_SET_DOC_FEE_CAPITALIZED_Y is not tracked here)
        testFeatureFlags.clear();
        testFeatureFlags.put("EC_INSURANCE_REDESIGN", false);
        
        Map<String, Boolean> newState = new HashMap<>(testFeatureFlags);
        newState.put("CQ_SET_DOC_FEE_CAPITALIZED_Y", true); // Always enabled
        response.put("previousState", previousState);
        response.put("newState", newState);
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("action", "RESET_TO_DEFAULTS");
        
        return response;
    }
    
    private Map<String, Object> getFeatureMetadata(String featureId, boolean enabled) {
        Map<String, Object> metadata = new HashMap<>();
        
        switch (featureId) {
            case "CQ_SET_DOC_FEE_CAPITALIZED_Y":
                metadata.put("description", "Enhanced document fee calculation with capitalized Y format (Always enabled, not a toggle)");
                metadata.put("category", "BILLING");
                metadata.put("impact", "UI_AND_CALCULATION");
                metadata.put("defaultValue", true);
                break;
                
            case "EC_INSURANCE_REDESIGN":
                metadata.put("description", "Redesigned insurance experience with AI-powered features");
                metadata.put("category", "UI_ENHANCEMENT");
                metadata.put("impact", "FULL_UI_REDESIGN");
                metadata.put("defaultValue", false);
                break;
                
            default:
                metadata.put("description", "Unknown feature");
                metadata.put("category", "UNKNOWN");
                metadata.put("impact", "UNKNOWN");
                metadata.put("defaultValue", false);
                break;
        }
        
        metadata.put("currentValue", enabled);
        metadata.put("lastChecked", LocalDateTime.now().toString());
        
        return metadata;
    }
}
