package com.example.demo1.service;

import com.example.demo1.feature.FeatureControlCheckUtil;
import org.springframework.stereotype.Service;

@Service
public class DocumentFeeService {
    
    public double calculateDocumentFee(double baseAmount) {
        // REMOVE: FeatureFlag logic. Assume feature always enabled/removed. Apply Y-cap logic directly if needed.
        return baseAmount; // Or baseAmount * <multiplier> if business logic stays.
    }
    
    public String getDocumentFeeDisplayFormat(double fee) {
        // REMOVE: FeatureFlag logic
        return String.format("$%.2f", fee);
    }
    
    public boolean isInsuranceProcessingEnabled() {
        return FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
    }
}
