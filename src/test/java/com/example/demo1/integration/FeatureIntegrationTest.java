package com.example.demo1.integration;

import com.example.demo1.feature.FeatureControlCheckUtil;
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
        // CQ_SET_DOC_FEE_CAPITALIZED_Y always enabled; only insurance flag is relevant
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
        // Only doc fee enabled
        Map<String, Object> config = configHelper.buildConfiguration(true, false);
        assertEquals("CAPITALIZED_Y", config.get("fee.display.format"));
        assertEquals("CLASSIC", config.get("insurance.ui.theme"));
        
        // Only insurance enabled
        config = configHelper.buildConfiguration(false, true);
        assertEquals("CAPITALIZED_Y", config.get("fee.display.format"));
        assertEquals("MODERN_REDESIGN", config.get("insurance.ui.theme"));
    }
    
    @Test
    void testFeatureValidationEdgeCases() {
        // Always enabled doc fee feature
        boolean docFeeEnabled = true;
        boolean insuranceEnabled = true;
        
        List<String> errors = documentValidator.validateDocument("legacy", 1000.0);
        assertTrue(errors.stream().anyMatch(error -> 
            error.contains("Legacy documents not supported with both enhanced features enabled")));
        
        // Test minimum amount validation
        errors = documentValidator.validateDocument("standard", 5.0);
        assertTrue(errors.stream().anyMatch(error -> 
            error.contains("Minimum amount for capitalized fee processing")));
    }
}
