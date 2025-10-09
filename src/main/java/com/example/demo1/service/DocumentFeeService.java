package com.example.demo1.service;

import org.springframework.stereotype.Service;

@Service
public class DocumentFeeService {
    
    public double calculateDocumentFee(double baseAmount) {
        // Feature is always enabled; always calculate as if enabled
        return baseAmount * 1.15;
    }
    
    public String getDocumentFeeDisplayFormat(double fee) {
        // Feature always enabled; always use capitalized format
        return String.format("$%.2f (CAPITALIZED)", fee);
    }
    
    public boolean isInsuranceProcessingEnabled() {
        // Existing logic or call for insurance; leave as is
        return false;
    }
}
