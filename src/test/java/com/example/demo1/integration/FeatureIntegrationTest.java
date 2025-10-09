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
        try (MockedStatic<FeatureControlCheckUtil> mockedStatic = Mockito.mockStatic(FeatureControlCheckUtil.class)) {
            // Store feature state in variable
            boolean insuranceEnabled = true;
            
            mockedStatic.when(FeatureControlCheckUtil::isEcInsuranceRedesignEnabled)
                      .thenReturn(insuranceEnabled);
            
            // Test configuration helper with feature values
            Map<String, Object> config = configHelper.buildConfiguration(false, insuranceEnabled);
            assertEquals("STANDARD", config.get("fee.display.format"));
            assertEquals("MODERN_REDESIGN", config.get("insurance.ui.theme"));
            assertEquals("PARTIAL_FEATURE_MODE", configHelper.determineSystemMode(false, insuranceEnabled));
            
            // Test validation with feature
            List<String> errors = documentValidator.validateDocument("insurance", 50.0);
            assertTrue(errors.isEmpty());
        }
    }
    
    @Test
    void testPartialFeatureScenarios() {
        try (MockedStatic<FeatureControlCheckUtil> mockedStatic = Mockito.mockStatic(FeatureControlCheckUtil.class)) {
            // Test insurance enabled
            mockedStatic.when(FeatureControlCheckUtil::isEcInsuranceRedesignEnabled)
                      .thenReturn(true);
            
            Map<String, Object> config = configHelper.buildConfiguration(false, true);
            assertEquals("STANDARD", config.get("fee.display.format"));
            assertEquals("MODERN_REDESIGN", config.get("insurance.ui.theme"));
        }
    }
}
