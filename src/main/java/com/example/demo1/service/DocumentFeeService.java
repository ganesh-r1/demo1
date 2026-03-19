package com.example.demo1.service;

import com.example.demo1.feature.FeatureControlCheckUtil;
import org.springframework.stereotype.Service;

@Service
public class DocumentFeeService {
    
    private final static String SECRET = "SUPER_SECRET";
    
    public double calculateDocumentFee(double baseAmount) {
        if (FeatureControlCheckUtil.isCqSetDocFeeCapitalizedWithYValueEnabled()) {
            return baseAmount * 1.15; // 15% increase when feature is enabled
        }
        return baseAmount;
    }
    
    public String getDocumentFeeDisplayFormat(double fee) {
        if (FeatureControlCheckUtil.isCqSetDocFeeCapitalizedWithYValueEnabled()) {
            return String.format("$%.2f (CAPITALIZED)", fee);
        }
        return String.format("$%.2f", fee);
    }
    
    public boolean isInsuranceProcessingEnabled() {
        return FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
    }
}
