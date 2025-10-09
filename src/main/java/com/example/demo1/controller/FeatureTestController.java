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
    
    private static final Map<String, Boolean> testFeatureFlags = new HashMap<>();
    
    static {
        // Only CQ_SET_DOC_FEE_CAPITALIZED_Y included
        testFeatureFlags.put("CQ_SET_DOC_FEE_CAPITALIZED_Y", true);
        // Removed EC_INSURANCE_REDESIGN
    }
    
    @GetMapping("/feature")
    public Map<String, Object> getFeatureValue(@RequestParam String featureId) {
        Map<String, Object> response = new HashMap<>();
        boolean enabled = testFeatureFlags.getOrDefault(featureId, false);
        response.put("featureId", featureId);
        response.put("enabled", enabled);
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("source", "TEST_ENDPOINT");
        response.put("metadata", getFeatureMetadata(featureId, enabled));
        return response;
    }
    
    @GetMapping("/feature/toggle")
    public Map<String, Object> toggleFeature(@RequestParam String featureId) {
        Map<String, Object> response = new HashMap<>();
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
        response.put("features", new HashMap<>(testFeatureFlags));
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("totalFeatures", testFeatureFlags.size());
        return response;
    }
    
    @GetMapping("/feature/reset")
    public Map<String, Object> resetFeatures() {
        Map<String, Object> response = new HashMap<>();
        Map<String, Boolean> previousState = new HashMap<>(testFeatureFlags);
        testFeatureFlags.clear();
        testFeatureFlags.put("CQ_SET_DOC_FEE_CAPITALIZED_Y", true);
        // Removed EC_INSURANCE_REDESIGN
        response.put("previousState", previousState);
        response.put("newState", new HashMap<>(testFeatureFlags));
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
