package com.example.demo1.service;

import com.example.demo1.feature.FeatureControlCheckUtil;
import org.springframework.stereotype.Service;

@Service
public class DocumentFeeService {
    public double calculateDocumentFee(double baseAmount) {
        // REMOVAL: always use former feature-enabled logic
        return baseAmount * 1.15;
    }
    public String getDocumentFeeDisplayFormat(double fee) {
        // Always use capitalized format
        return String.format("$%.2f (CAPITALIZED)", fee);
    }
    public boolean isInsuranceProcessingEnabled() {
        return FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
    }
}
