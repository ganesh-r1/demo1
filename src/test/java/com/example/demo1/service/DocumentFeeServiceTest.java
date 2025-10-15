package com.example.demo1.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class DocumentFeeServiceTest {
    
    private DocumentFeeService documentFeeService;
    
    @BeforeEach
    void setUp() {
        documentFeeService = new DocumentFeeService();
    }
    
    @Test
    void testCalculateDocumentFeeWithCapitalizedFeatureEnabled() {
        // CQ_SET_DOC_FEE_CAPITALIZED_Y is always enabled
        double result = documentFeeService.calculateDocumentFee(100.0);
        assertEquals(115.0, result, 0.01);
        String displayFormat = documentFeeService.getDocumentFeeDisplayFormat(result);
        assertTrue(displayFormat.contains("CAPITALIZED"));
    }
    
    @Test
    void testCalculateDocumentFeeWithCapitalizedFeatureDisabled() {
        // Feature flag is always enabled; this scenario is obsolete
        double result = documentFeeService.calculateDocumentFee(100.0);
        assertEquals(115.0, result, 0.01);
    }
    
    @Test
    void testInsuranceProcessingFeatureCheck() {
        // Test insurance redesign feature
        // The state of EC_INSURANCE_REDESIGN can still be toggled in the implementation
        assertTrue(documentFeeService.isInsuranceProcessingEnabled());
    }
    
    @Test
    void testCombinedFeatureScenarios() {
        // Test scenario with both features enabled
        double feeResult = documentFeeService.calculateDocumentFee(200.0);
        boolean insuranceResult = documentFeeService.isInsuranceProcessingEnabled();
        assertEquals(230.0, feeResult, 0.01);
        assertTrue(insuranceResult);
    }
}
