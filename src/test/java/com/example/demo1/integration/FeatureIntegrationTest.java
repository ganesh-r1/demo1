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
    void testInsuranceFeatureEnabledScenario() {
        try (MockedStatic<FeatureControlCheckUtil> mockedStatic = Mockito.mockStatic(FeatureControlCheckUtil.class)) {
            boolean insuranceEnabled = true;
            mockedStatic.when(FeatureControlCheckUtil::isEcInsuranceRedesignEnabled)
                      .thenReturn(insuranceEnabled);
            
            Map<String, Object> config = configHelper.buildConfiguration(false, insuranceEnabled);
            assertEquals("STANDARD", config.get("fee.display.format"));
            assertEquals("MODERN_REDESIGN", config.get("insurance.ui.theme"));
            assertEquals("PARTIAL_FEATURE_MODE", configHelper.determineSystemMode(false, insuranceEnabled));
            
            List<String> errors = documentValidator.validateDocument("insurance", 50.0);
            assertTrue(errors.isEmpty());
        }
    }
    
    @Test
    void testInsuranceFeatureDisabledScenario() {
        try (MockedStatic<FeatureControlCheckUtil> mockedStatic = Mockito.mockStatic(FeatureControlCheckUtil.class)) {
            mockedStatic.when(FeatureControlCheckUtil::isEcInsuranceRedesignEnabled)
                      .thenReturn(false);
            
            Map<String, Object> config = configHelper.buildConfiguration(false, false);
            assertEquals("STANDARD", config.get("fee.display.format"));
            assertEquals("CLASSIC", config.get("insurance.ui.theme"));
        }
    }
    
    @Test
    void testFeatureValidationEdgeCases() {
        try (MockedStatic<FeatureControlCheckUtil> mockedStatic = Mockito.mockStatic(FeatureControlCheckUtil.class)) {
            mockedStatic.when(FeatureControlCheckUtil::isEcInsuranceRedesignEnabled)
                      .thenReturn(true);
            
            List<String> errors = documentValidator.validateDocument("invalid_insurance", 1000.0);
            assertTrue(errors.stream().anyMatch(error -> 
                error.contains("Document does not meet enhanced insurance validation requirements")));
        }
    }
}
