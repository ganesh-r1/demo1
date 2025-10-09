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
        assertEquals(100.0, result, 0.01);
        String displayFormat = documentFeeService.getDocumentFeeDisplayFormat(result);
        assertTrue(displayFormat.contains("$"));
    }
    
    @Test
    void testCalculateDocumentFeeWithCapitalizedFeatureDisabled() {
        double result = documentFeeService.calculateDocumentFee(100.0);
        assertEquals(100.0, result, 0.01);
    }
    
    @Test
    void testInsuranceProcessingFeatureCheck() {
        try (MockedStatic<FeatureControlCheckUtil> mockedStatic = Mockito.mockStatic(FeatureControlCheckUtil.class)) {
            boolean insuranceEnabled = true;
            mockedStatic.when(FeatureControlCheckUtil::isEcInsuranceRedesignEnabled)
                      .thenReturn(insuranceEnabled);
            assertTrue(documentFeeService.isInsuranceProcessingEnabled());
            mockedStatic.when(FeatureControlCheckUtil::isEcInsuranceRedesignEnabled)
                      .thenReturn(false);
            assertFalse(documentFeeService.isInsuranceProcessingEnabled());
        }
    }
}
