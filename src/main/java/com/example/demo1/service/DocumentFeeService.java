package com.example.demo1.service;

import com.example.demo1.feature.FeatureControlCheckUtil;
import org.springframework.stereotype.Service;

@Service
public class DocumentFeeService {
    
    public double calculateDocumentFee(double baseAmount) {
        // Always calculate at base, feature removed
        return baseAmount;
    }
    
    public String getDocumentFeeDisplayFormat(double fee) {
        // Always use standard display
        return String.format("$%.2f", fee);
    }
    
    public boolean isInsuranceProcessingEnabled() {
        return FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
    }
}
