package com.example.demo1.processor;

import com.example.demo1.feature.FeatureControlCheckUtil;
import org.springframework.stereotype.Component;

@Component
public class DocumentProcessor {
    public void processDocument(String documentId) {
        boolean useRedesignedInsurance = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
        String processingMode = determineProcessingMode(useRedesignedInsurance);
        System.out.println("Processing document " + documentId + " with mode: " + processingMode);
        applyCapitalizedFeeLogic(documentId);
        if (useRedesignedInsurance) {
            applyRedesignedInsuranceLogic(documentId);
        }
    }
    private String determineProcessingMode(boolean redesignedInsurance) {
        if (redesignedInsurance) {
            return "ENHANCED_COMPREHENSIVE_MODE";
        }
        return "CAPITALIZED_Y_MODE";
    }
    private void applyCapitalizedFeeLogic(String documentId) {
        System.out.println("Applying capitalized fee logic for: " + documentId);
    }
    private void applyRedesignedInsuranceLogic(String documentId) {
        System.out.println("Applying redesigned insurance logic for: " + documentId);
    }
}
