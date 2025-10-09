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
        try (MockedStatic<FeatureControlCheckUtil> mockedStatic = Mockito.mockStatic(FeatureControlCheckUtil.class)) {
            boolean featureEnabled = true;
            mockedStatic.when(FeatureControlCheckUtil::isCqSetDocFeeCapitalizedWithYValueEnabled)
                      .thenReturn(featureEnabled);
            double result = documentFeeService.calculateDocumentFee(100.0);
            assertEquals(115.0, result, 0.01);
            String displayFormat = documentFeeService.getDocumentFeeDisplayFormat(result);
            assertTrue(displayFormat.contains("CAPITALIZED"));
        }
    }
    
    @Test
    void testCalculateDocumentFeeWithCapitalizedFeatureDisabled() {
        try (MockedStatic<FeatureControlCheckUtil> mockedStatic = Mockito.mockStatic(FeatureControlCheckUtil.class)) {
            boolean featureEnabled = false;
            mockedStatic.when(FeatureControlCheckUtil::isCqSetDocFeeCapitalizedWithYValueEnabled)
                      .thenReturn(featureEnabled);
            double result = documentFeeService.calculateDocumentFee(100.0);
            assertEquals(100.0, result, 0.01);
        }
    }
    
    // REMOVE: insurance processing feature check
    
    @Test
    void testCombinedFeatureScenarios() {
        try (MockedStatic<FeatureControlCheckUtil> mockedStatic = Mockito.mockStatic(FeatureControlCheckUtil.class)) {
            // Test scenario with doc fee enabled
            mockedStatic.when(FeatureControlCheckUtil::isCqSetDocFeeCapitalizedWithYValueEnabled)
                      .thenReturn(true);
            double feeResult = documentFeeService.calculateDocumentFee(200.0);
            assertEquals(230.0, feeResult, 0.01);
        }
    }
}
