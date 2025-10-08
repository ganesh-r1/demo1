package com.example.demo1.integration;

import com.example.demo1.feature.FeatureControlCheckUtil;
import com.example.demo1.helper.FeatureConfigHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
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
    void testFullFeatureScenarioAlwaysCapitalized() {
        boolean insuranceEnabled = true;
        // CQ_SET_DOC_FEE_CAPITALIZED_Y always enabled
        Map<String, Object> config = configHelper.buildConfiguration(true, insuranceEnabled);
        assertEquals("CAPITALIZED_Y", config.get("fee.display.format"));
        assertEquals("MODERN_REDESIGN", config.get("insurance.ui.theme"));
        assertEquals("FULL_FEATURE_MODE", configHelper.determineSystemMode(true, insuranceEnabled));
        List<String> errors = documentValidator.validateDocument("insurance", 50.0);
        assertTrue(errors.isEmpty());
    }
    @Test
    void testPartialFeatureScenarios() {
        // Only insurance feature toggled
        Map<String, Object> config = configHelper.buildConfiguration(true, false);
        assertEquals("CAPITALIZED_Y", config.get("fee.display.format"));
        assertEquals("CLASSIC", config.get("insurance.ui.theme"));
        // Insurance enabled
        config = configHelper.buildConfiguration(true, true);
        assertEquals("CAPITALIZED_Y", config.get("fee.display.format"));
        assertEquals("MODERN_REDESIGN", config.get("insurance.ui.theme"));
    }
    @Test
    void testFeatureValidationEdgeCases() {
        // Always capitalized fee process, only insurance state toggled
        List<String> errors = documentValidator.validateDocument("legacy", 1000.0);
        assertTrue(errors.stream().anyMatch(error -> error.contains("Legacy documents not supported with both enhanced features enabled")));
        errors = documentValidator.validateDocument("standard", 5.0);
        assertTrue(errors.stream().anyMatch(error -> error.contains("Minimum amount for capitalized fee processing")));
    }
}
