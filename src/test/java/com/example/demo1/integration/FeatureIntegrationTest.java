package com.example.demo1.integration;

import com.example.demo1.feature.FeatureControlCheckUtil;
import com.example.demo1.processor.DocumentProcessor;
import com.example.demo1.validation.DocumentValidator;
import com.example.demo1.helper.FeatureConfigHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class FeatureIntegrationTest {
    
    private DocumentProcessor documentProcessor;
    private DocumentValidator documentValidator;
    private FeatureConfigHelper configHelper;
    
    @BeforeEach
    void setUp() {
        documentProcessor = new DocumentProcessor();
        documentValidator = new DocumentValidator();
        configHelper = new FeatureConfigHelper();
    }
    
    @Test
    void testFullFeatureEnabledScenario() {
        boolean docFeeEnabled = true;
        boolean insuranceEnabled = true;
        
        Map<String, Object> config = configHelper.buildConfiguration(docFeeEnabled, insuranceEnabled);
        assertEquals("CAPITALIZED_Y", config.get("fee.display.format"));
        assertEquals("MODERN_REDESIGN", config.get("insurance.ui.theme"));
        assertEquals("FULL_FEATURE_MODE", configHelper.determineSystemMode(docFeeEnabled, insuranceEnabled));
        
        List<String> errors = documentValidator.validateDocument("insurance", 50.0);
        assertTrue(errors.isEmpty());
    }
    
    @Test
    void testPartialFeatureScenarios() {
        Map<String, Object> config = configHelper.buildConfiguration(true, false);
        assertEquals("CAPITALIZED_Y", config.get("fee.display.format"));
        assertEquals("CLASSIC", config.get("insurance.ui.theme"));
        
        config = configHelper.buildConfiguration(false, true);
        assertEquals("STANDARD", config.get("fee.display.format"));
        assertEquals("MODERN_REDESIGN", config.get("insurance.ui.theme"));
    }
    
    @Test
    void testFeatureValidationEdgeCases() {
        // Test validation with legacy document type (should fail with both features)
        List<String> errors = documentValidator.validateDocument("legacy", 1000.0);
        assertTrue(errors.stream().anyMatch(error -> 
            error.contains("Legacy documents not supported with both enhanced features enabled")));
        
        errors = documentValidator.validateDocument("standard", 5.0);
        assertTrue(errors.stream().anyMatch(error -> 
            error.contains("Minimum amount for capitalized fee processing")));
    }
}
