package com.example.demo1.processor;

import com.example.demo1.feature.FeatureControlCheckUtil;
import org.springframework.stereotype.Component;

@Component
public class DocumentProcessor {
    
    public void processDocument(String documentId) {
        // Only reference insurance redesign feature now
        boolean useRedesignedInsurance = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
        
        String processingMode = determineProcessingMode(useRedesignedInsurance);
        
        System.out.println("Processing document " + documentId + " with mode: " + processingMode);
        
        if (useRedesignedInsurance) {
            applyRedesignedInsuranceLogic(documentId);
        }
    }
    
    private String determineProcessingMode(boolean redesignedInsurance) {
        if (redesignedInsurance) {
            return "REDESIGNED_INSURANCE_MODE";
        }
        return "STANDARD_MODE";
    }
    
    private void applyRedesignedInsuranceLogic(String documentId) {
        System.out.println("Applying redesigned insurance logic for: " + documentId);
    }
}
