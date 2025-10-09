package com.example.demo1.processor;

import com.example.demo1.feature.FeatureControlCheckUtil;
import org.springframework.stereotype.Component;

@Component
public class DocumentProcessor {
    
    public void processDocument(String documentId) {
        boolean useRedesignedInsurance = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
        String processingMode = useRedesignedInsurance ? "ENHANCED_COMPREHENSIVE_MODE" : "STANDARD_MODE";
        System.out.println("Processing document " + documentId + " with mode: " + processingMode);
        applyCapitalizedFeeLogic(documentId); // Always apply, feature is removed
        if (useRedesignedInsurance) {
            applyRedesignedInsuranceLogic(documentId);
        }
    }
    private void applyCapitalizedFeeLogic(String documentId) {
        System.out.println("Applying capitalized fee logic for: " + documentId);
    }
    private void applyRedesignedInsuranceLogic(String documentId) {
        System.out.println("Applying redesigned insurance logic for: " + documentId);
    }
}
