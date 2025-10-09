package com.example.demo1.service;

import com.example.demo1.feature.FeatureControlCheckUtil;
import org.springframework.stereotype.Service;

@Service
public class DocumentFeeService {
    
    public double calculateDocumentFee(double baseAmount) {
        // CQ_SET_DOC_FEE_CAPITALIZED_Y always enabled
        return baseAmount * 1.15;
    }
    
    public String getDocumentFeeDisplayFormat(double fee) {
        // CQ_SET_DOC_FEE_CAPITALIZED_Y always enabled
        return String.format("$%.2f (CAPITALIZED)", fee);
    }
    
    public boolean isInsuranceProcessingEnabled() {
        return FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
    }
}
