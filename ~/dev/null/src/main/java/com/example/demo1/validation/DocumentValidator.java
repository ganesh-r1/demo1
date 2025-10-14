package com.example.demo1.validation;

import com.example.demo1.feature.FeatureControlCheckUtil;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.ArrayList;

@Component
public class DocumentValidator {
    
    public List<String> validateDocument(String documentType, double amount) {
        List<String> validationErrors = new ArrayList<>();
        
        boolean docFeeCapitalizedEnabled = FeatureControlCheckUtil.isCqSetDocFeeCapitalizedWithYValueEnabled();
        
        validateAmount(validationErrors, amount, docFeeCapitalizedEnabled);
        validateDocumentType(validationErrors, documentType);
        validateFeatureCompatibility(validationErrors, documentType, docFeeCapitalizedEnabled);
        
        return validationErrors;
    }
    
    private void validateAmount(List<String> errors, double amount, boolean capitalizedFeeEnabled) {
        if (capitalizedFeeEnabled && amount < 10.0) {
            errors.add("Minimum amount for capitalized fee processing is $10.00");
        }
        
        if (capitalizedFeeEnabled && amount > 100000.0) {
            errors.add("Amount exceeds maximum for Y-capitalized processing");
        }
    }
    
    private void validateDocumentType(List<String> errors, String documentType) {
        // No insurance redesign logic
    }
    
    private void validateFeatureCompatibility(List<String> errors, String documentType, 
                                            boolean docFeeEnabled) {
        // No insurance redesign logic
    }
    
    private boolean isEnhancedValidationPassed(String documentType) {
        return !"invalid_insurance".equals(documentType);
    }
}