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
        try (MockedStatic<FeatureControlCheckUtil> mockedStatic = Mockito.mockStatic(FeatureControlCheckUtil.class)) {
            boolean insuranceEnabled = true;
            mockedStatic.when(FeatureControlCheckUtil::isEcInsuranceRedesignEnabled)
                      .thenReturn(insuranceEnabled);
            Map<String, Object> config = configHelper.buildConfiguration(insuranceEnabled);
            assertEquals("CAPITALIZED_Y", config.get("fee.display.format"));
            assertEquals("MODERN_REDESIGN", config.get("insurance.ui.theme"));
            assertEquals("FULL_FEATURE_MODE", configHelper.determineSystemMode(insuranceEnabled));
            List<String> errors = documentValidator.validateDocument("insurance", 50.0);
            assertTrue(errors.isEmpty());
        }
    }
    @Test
    void testPartialFeatureScenarios() {
        try (MockedStatic<FeatureControlCheckUtil> mockedStatic = Mockito.mockStatic(FeatureControlCheckUtil.class)) {
            // Only insurance enabled
            mockedStatic.when(FeatureControlCheckUtil::isEcInsuranceRedesignEnabled)
                      .thenReturn(true);
            Map<String, Object> config = configHelper.buildConfiguration(true);
            assertEquals("CAPITALIZED_Y", config.get("fee.display.format"));
            assertEquals("MODERN_REDESIGN", config.get("insurance.ui.theme"));
            // Insurance disabled
            mockedStatic.when(FeatureControlCheckUtil::isEcInsuranceRedesignEnabled)
                      .thenReturn(false);
            config = configHelper.buildConfiguration(false);
            assertEquals("CAPITALIZED_Y", config.get("fee.display.format"));
            assertEquals("CLASSIC", config.get("insurance.ui.theme"));
        }
    }
    @Test
    void testFeatureValidationEdgeCases() {
        try (MockedStatic<FeatureControlCheckUtil> mockedStatic = Mockito.mockStatic(FeatureControlCheckUtil.class)) {
            mockedStatic.when(FeatureControlCheckUtil::isEcInsuranceRedesignEnabled)
                      .thenReturn(true);
            List<String> errors = documentValidator.validateDocument("legacy", 1000.0);
            assertTrue(errors.stream().anyMatch(error -> 
                error.contains("Legacy documents not supported with enhanced insurance redesign enabled")));
            errors = documentValidator.validateDocument("standard", 5.0);
            assertTrue(errors.stream().anyMatch(error -> 
                error.contains("Minimum amount for capitalized fee processing")));
        }
    }
}
