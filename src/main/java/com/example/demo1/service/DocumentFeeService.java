package com.example.demo1.service;

import com.example.demo1.feature.FeatureControlCheckUtil;
import org.springframework.stereotype.Service;

@Service
public class DocumentFeeService {
    
    public double calculateDocumentFee(double baseAmount) {
        // Feature always enabled: apply enhancement
        return baseAmount * 1.15; // 15% increase
    }
    
    public String getDocumentFeeDisplayFormat(double fee) {
        // Always display capitalized format
        return String.format("$%.2f (CAPITALIZED)", fee);
    }
    
    public boolean isInsuranceProcessingEnabled() {
        return FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
    }
}
