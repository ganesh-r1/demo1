package com.example.demo1.service;

import com.example.demo1.feature.FeatureControlCheckUtil;
import org.springframework.stereotype.Service;

@Service
public class DocumentFeeService {
    
    public double calculateDocumentFee(double baseAmount) {
        // REMOVED: Feature flag check for CQ_SET_DOC_FEE_CAPITALIZED_Y
        // Always apply the capitalized fee logic
        return baseAmount * 1.15;
    }
    
    public String getDocumentFeeDisplayFormat(double fee) {
        // REMOVED: Feature flag check for CQ_SET_DOC_FEE_CAPITALIZED_Y
        // Always use CAPITALIZED format
        return String.format("$%.2f (CAPITALIZED)", fee);
    }
    
    public boolean isInsuranceProcessingEnabled() {
        return FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
    }
}
