package com.example.demo1.validation;

import com.example.demo1.feature.FeatureControlCheckUtil;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.ArrayList;

@Component
public class DocumentValidator {
    
    public List<String> validateDocument(String documentType, double amount) {
        List<String> validationErrors = new ArrayList<>();
        
        // Only reference insurance feature
        boolean insuranceRedesignEnabled = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
        
        validateDocumentType(validationErrors, documentType, insuranceRedesignEnabled);
        return validationErrors;
    }
    
    private void validateDocumentType(List<String> errors, String documentType, boolean insuranceRedesignEnabled) {
        if (insuranceRedesignEnabled && "insurance".equals(documentType)) {
            if (!isEnhancedValidationPassed(documentType)) {
                errors.add("Document does not meet enhanced insurance validation requirements");
            }
        }
    }
    
    private boolean isEnhancedValidationPassed(String documentType) {
        return !"invalid_insurance".equals(documentType);
    }
}
