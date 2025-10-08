package com.example.demo1.processor;

import com.example.demo1.feature.FeatureControlCheckUtil;
import org.springframework.stereotype.Component;

@Component
public class DocumentProcessor {
    
    public void processDocument(String documentId) {
        // Indirect reference via intermediate variables
        boolean useCapitalizedFeeFormat = FeatureControlCheckUtil.isCqSetDocFeeCapitalizedWithYValueEnabled();
        boolean useRedesignedInsurance = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
        
        String processingMode = determineProcessingMode(useCapitalizedFeeFormat, useRedesignedInsurance);
        
        System.out.println("Processing document " + documentId + " with mode: " + processingMode);
        
        if (useCapitalizedFeeFormat) {
            applyCapitalizedFeeLogic(documentId);
        }
        
        if (useRedesignedInsurance) {
            applyRedesignedInsuranceLogic(documentId);
        }
    }
    
    private String determineProcessingMode(boolean capitalizedFee, boolean redesignedInsurance) {
        if (capitalizedFee && redesignedInsurance) {
            return "ENHANCED_COMPREHENSIVE_MODE";
        } else if (capitalizedFee) {
            return "CAPITALIZED_Y_MODE";
        } else if (redesignedInsurance) {
            return "REDESIGNED_INSURANCE_MODE";
        }
        return "STANDARD_MODE";
    }
    
    private void applyCapitalizedFeeLogic(String documentId) {
        System.out.println("Applying capitalized fee logic for: " + documentId);
    }
    
    private void applyRedesignedInsuranceLogic(String documentId) {
        System.out.println("Applying redesigned insurance logic for: " + documentId);
    }
}
