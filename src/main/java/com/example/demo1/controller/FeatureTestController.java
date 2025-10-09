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
        // CQ_SET_DOC_FEE_CAPITALIZED_Y removed; always enabled
        // testFeatureFlags.put("CQ_SET_DOC_FEE_CAPITALIZED_Y", true);
        testFeatureFlags.put("EC_INSURANCE_REDESIGN", false);
    }
    
    @GetMapping("/feature")
    public Map<String, Object> getFeatureValue(@RequestParam String featureId) {
        Map<String, Object> response = new HashMap<>();
        
        // CQ_SET_DOC_FEE_CAPITALIZED_Y removed; always enabled
        boolean enabled = "CQ_SET_DOC_FEE_CAPITALIZED_Y".equals(featureId) ? true : testFeatureFlags.getOrDefault(featureId, false);
        
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
        
        if ("CQ_SET_DOC_FEE_CAPITALIZED_Y".equals(featureId)) {
            // Do nothing; always enabled.
            response.put("featureId", featureId);
            response.put("previousValue", true);
            response.put("newValue", true);
            response.put("timestamp", LocalDateTime.now().toString());
            response.put("action", "NOOP_ALWAYS_ENABLED");
            return response;
        }

        // Toggle the feature value
        boolean currentValue = testFeatureFlags.getOrDefault(featureId, false);
        boolean newValue = !currentValue;
        testFeatureFlags.put(featureId, newValue);
        
        response.put("featureId", featureId);
        response.put("previousValue", currentValue);
        response.put("newValue", newValue);
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("action", "TOGGLED");
        
        return response;
    }
    
    @GetMapping("/feature/set")
    public Map<String, Object> setFeature(@RequestParam String featureId, 
                                        @RequestParam boolean enabled) {
        Map<String, Object> response = new HashMap<>();
        
        if ("CQ_SET_DOC_FEE_CAPITALIZED_Y".equals(featureId)) {
            response.put("featureId", featureId);
            response.put("previousValue", true);
            response.put("newValue", true);
            response.put("timestamp", LocalDateTime.now().toString());
            response.put("action", "NOOP_ALWAYS_ENABLED");
            return response;
        }

        boolean previousValue = testFeatureFlags.getOrDefault(featureId, false);
        testFeatureFlags.put(featureId, enabled);
        
        response.put("featureId", featureId);
        response.put("previousValue", previousValue);
        response.put("newValue", enabled);
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("action", "SET");
        
        return response;
    }
    
    @GetMapping("/feature/all")
    public Map<String, Object> getAllFeatures() {
        Map<String, Object> response = new HashMap<>();
        Map<String, Boolean> allFeatures = new HashMap<>(testFeatureFlags);
        allFeatures.put("CQ_SET_DOC_FEE_CAPITALIZED_Y", true);
        response.put("features", allFeatures);
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("totalFeatures", allFeatures.size());
        
        return response;
    }
    
    @GetMapping("/feature/reset")
    public Map<String, Object> resetFeatures() {
        Map<String, Object> response = new HashMap<>();
        
        // Store previous state
        Map<String, Boolean> previousState = new HashMap<>(testFeatureFlags);
        
        // Reset to defaults
        testFeatureFlags.clear();
        // CQ_SET_DOC_FEE_CAPITALIZED_Y always enabled; do not reset
        testFeatureFlags.put("EC_INSURANCE_REDESIGN", false);
        
        response.put("previousState", previousState);
        Map<String, Boolean> newState = new HashMap<>(testFeatureFlags);
        newState.put("CQ_SET_DOC_FEE_CAPITALIZED_Y", true);
        response.put("newState", newState);
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("action", "RESET_TO_DEFAULTS");
        
        return response;
    }
    
    private Map<String, Object> getFeatureMetadata(String featureId, boolean enabled) {
        Map<String, Object> metadata = new HashMap<>();
        
        switch (featureId) {
            case "CQ_SET_DOC_FEE_CAPITALIZED_Y":
                metadata.put("description", "Enhanced document fee calculation with capitalized Y format");
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
