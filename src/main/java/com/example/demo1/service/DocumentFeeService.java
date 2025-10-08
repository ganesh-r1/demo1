package com.example.demo1.service;

import com.example.demo1.feature.FeatureControlCheckUtil;
import org.springframework.stereotype.Service;

@Service
public class DocumentFeeService {
    public double calculateDocumentFee(double baseAmount) {
        if (FeatureControlCheckUtil.isCqSetDocFeeCapitalizedWithYValueEnabled()) {
            return baseAmount * 1.15;
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
        return true;
    }
}
