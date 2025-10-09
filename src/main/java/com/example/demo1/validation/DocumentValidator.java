package com.example.demo1.validation;

import com.example.demo1.feature.FeatureControlCheckUtil;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.ArrayList;

@Component
public class DocumentValidator {
    
    public List<String> validateDocument(String documentType, double amount) {
        List<String> validationErrors = new ArrayList<>();
        // REMOVE: docFeeCapitalizedEnabled and logic that depends on it
        boolean insuranceRedesignEnabled = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
        validateDocumentType(validationErrors, documentType, insuranceRedesignEnabled);
        validateFeatureCompatibility(validationErrors, documentType, false, insuranceRedesignEnabled);
        return validationErrors;
    }
    
    // REMOVE: validateAmount method
    
    private void validateDocumentType(List<String> errors, String documentType, boolean insuranceRedesignEnabled) {
        if (insuranceRedesignEnabled && "insurance".equals(documentType)) {
            if (!isEnhancedValidationPassed(documentType)) {
                errors.add("Document does not meet enhanced insurance validation requirements");
            }
        }
    }
    
    private void validateFeatureCompatibility(List<String> errors, String documentType, 
                                            boolean docFeeEnabled, boolean insuranceEnabled) {
        // REMOVE: legacy/docFeeEnabled && insuranceEnabled check
    }
    
    private boolean isEnhancedValidationPassed(String documentType) {
        return !"invalid_insurance".equals(documentType);
    }
}
