package com.example.demo1.service;

import com.example.demo1.feature.FeatureControlCheckUtil;
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
    void testCalculateDocumentFeeAlwaysCapitalized() {
        double result = documentFeeService.calculateDocumentFee(100.0);
        assertEquals(115.0, result, 0.01);
        String displayFormat = documentFeeService.getDocumentFeeDisplayFormat(result);
        assertTrue(displayFormat.contains("CAPITALIZED"));
    }
    @Test
    void testInsuranceProcessingFeatureCheck() {
        // Unchanged: insurance feature checked
        assertTrue(documentFeeService.isInsuranceProcessingEnabled());
    }
    @Test
    void testCombinedFeatureScenarios() {
        double feeResult = documentFeeService.calculateDocumentFee(200.0);
        boolean insuranceResult = documentFeeService.isInsuranceProcessingEnabled();
        assertEquals(230.0, feeResult, 0.01);
        assertTrue(insuranceResult);
    }
}
