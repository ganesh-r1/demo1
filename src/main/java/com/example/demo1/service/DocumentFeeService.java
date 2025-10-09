package com.example.demo1.service;

import com.example.demo1.feature.FeatureControlCheckUtil;
import org.springframework.stereotype.Service;

@Service
public class DocumentFeeService {
    
    public double calculateDocumentFee(double baseAmount) {
        // Doc fee capitalization feature always enabled
        return baseAmount * 1.15; // 15% increase
    }
    
    public String getDocumentFeeDisplayFormat(double fee) {
        // Doc fee capitalization display always enabled
        return String.format("$%.2f (CAPITALIZED)", fee);
    }
    
    public boolean isInsuranceProcessingEnabled() {
        return FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
    }
}
