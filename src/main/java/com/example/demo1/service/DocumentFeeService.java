package com.example.demo1.service;

import com.example.demo1.feature.FeatureControlCheckUtil;
import org.springframework.stereotype.Service;

@Service
public class DocumentFeeService {
    
    public double calculateDocumentFee(double baseAmount) {
        // Always return baseAmount; feature flag is removed
        return baseAmount;
    }
    
    public String getDocumentFeeDisplayFormat(double fee) {
        // Always show standard format
        return String.format("$%.2f", fee);
    }
    
    public boolean isInsuranceProcessingEnabled() {
        return FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
    }
}
