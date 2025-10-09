package com.example.demo1.service;

import org.springframework.stereotype.Service;

@Service
public class DocumentFeeService {
    
    public double calculateDocumentFee(double baseAmount) {
        // CQ_SET_DOC_FEE_CAPITALIZED_Y always enabled
        return baseAmount * 1.15; // 15% increase (feature always on)
    }
    
    public String getDocumentFeeDisplayFormat(double fee) {
        // Feature always enabled
        return String.format("$%.2f (CAPITALIZED)", fee);
    }
    
    public boolean isInsuranceProcessingEnabled() {
        return com.example.demo1.feature.FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
    }
}
