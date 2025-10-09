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
            boolean docFeeEnabled = true;
            // REMOVED: boolean insuranceEnabled = true;
            mockedStatic.when(FeatureControlCheckUtil::isCqSetDocFeeCapitalizedWithYValueEnabled)
                      .thenReturn(docFeeEnabled);
            // REMOVED: insurance feature flag mocking
            Map<String, Object> config = configHelper.buildConfiguration(docFeeEnabled, false);
            assertEquals("CAPITALIZED_Y", config.get("fee.display.format"));
            assertEquals("CLASSIC", config.get("insurance.ui.theme"));
            assertEquals("PARTIAL_FEATURE_MODE", configHelper.determineSystemMode(docFeeEnabled, false));
            List<String> errors = documentValidator.validateDocument("insurance", 50.0);
            assertTrue(errors.isEmpty());
        }
    }
    
    @Test
    void testPartialFeatureScenarios() {
        try (MockedStatic<FeatureControlCheckUtil> mockedStatic = Mockito.mockStatic(FeatureControlCheckUtil.class)) {
            mockedStatic.when(FeatureControlCheckUtil::isCqSetDocFeeCapitalizedWithYValueEnabled)
                      .thenReturn(true);
            Map<String, Object> config = configHelper.buildConfiguration(true, false);
            assertEquals("CAPITALIZED_Y", config.get("fee.display.format"));
            assertEquals("CLASSIC", config.get("insurance.ui.theme"));
            mockedStatic.when(FeatureControlCheckUtil::isCqSetDocFeeCapitalizedWithYValueEnabled)
                      .thenReturn(false);
            config = configHelper.buildConfiguration(false, false);
            assertEquals("STANDARD", config.get("fee.display.format"));
            assertEquals("CLASSIC", config.get("insurance.ui.theme"));
        }
    }
    
    @Test
    void testFeatureValidationEdgeCases() {
        try (MockedStatic<FeatureControlCheckUtil> mockedStatic = Mockito.mockStatic(FeatureControlCheckUtil.class)) {
            mockedStatic.when(FeatureControlCheckUtil::isCqSetDocFeeCapitalizedWithYValueEnabled)
                      .thenReturn(true);
            List<String> errors = documentValidator.validateDocument("legacy", 1000.0);
            assertTrue(errors.stream().anyMatch(error -> 
                error.contains("Legacy documents not supported with enhanced feature enabled")));
            errors = documentValidator.validateDocument("standard", 5.0);
            assertTrue(errors.stream().anyMatch(error -> 
                error.contains("Minimum amount for capitalized fee processing")));
        }
    }
}
