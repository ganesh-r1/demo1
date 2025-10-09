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
    private void validateFeatureCompatibility(List<String> errors, String documentType, boolean docFeeEnabled) {
        // The logic for legacy document+insuranceEnabled is removed
    }
}
