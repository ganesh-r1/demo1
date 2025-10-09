package com.example.demo1.service;

import org.springframework.stereotype.Service;

@Service
public class DocumentFeeService {
    public double calculateDocumentFee(double baseAmount) {
        return baseAmount * 1.15;
    }
    public String getDocumentFeeDisplayFormat(double fee) {
        return String.format("$%.2f (CAPITALIZED)", fee);
    }
    public boolean isInsuranceProcessingEnabled() {
        return true;
    }
}
