package com.example.demo1.service;

import com.example.demo1.feature.FeatureControlCheckUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;

public class DocumentFeeServiceTest {
    
    private DocumentFeeService documentFeeService;
    
    @BeforeEach
    void setUp() {
        documentFeeService = new DocumentFeeService();
    }
    
    @Test
    void testCalculateDocumentFeeWithCapitalizedFeatureEnabled() {
        double result = documentFeeService.calculateDocumentFee(100.0);
        assertEquals(115.0, result, 0.01);
        
        String displayFormat = documentFeeService.getDocumentFeeDisplayFormat(result);
        assertTrue(displayFormat.contains("CAPITALIZED"));
    }
    
    @Test
    void testCalculateDocumentFeeWithCapitalizedFeatureDisabled() {
        // Feature always enabled, expect capitalized logic
        double result = documentFeeService.calculateDocumentFee(100.0);
        assertEquals(115.0, result, 0.01);
    }
    
    @Test
    void testInsuranceProcessingFeatureCheck() {
        try (MockedStatic<FeatureControlCheckUtil> mockedStatic = Mockito.mockStatic(FeatureControlCheckUtil.class)) {
            // Test both feature states
            boolean insuranceEnabled = true;
            mockedStatic.when(FeatureControlCheckUtil::isEcInsuranceRedesignEnabled)
                      .thenReturn(insuranceEnabled);
            
            assertTrue(documentFeeService.isInsuranceProcessingEnabled());
            
            mockedStatic.when(FeatureControlCheckUtil::isEcInsuranceRedesignEnabled)
                      .thenReturn(false);
            
            assertFalse(documentFeeService.isInsuranceProcessingEnabled());
        }
    }
    
    @Test
    void testCombinedFeatureScenarios() {
        // Feature always enabled
        double feeResult = documentFeeService.calculateDocumentFee(200.0);
        assertEquals(230.0, feeResult, 0.01);
    }
}
