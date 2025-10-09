package com.example.demo1.billing;

import com.example.demo1.feature.FeatureControlCheckUtil;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

@Component
public class InvoiceGenerator {
    
    public List<String> generateInvoiceItems(BigDecimal baseAmount, String clientType) {
        List<String> items = new ArrayList<>();
        
        // Only reference insurance redesign feature
        boolean insuranceRedesignActive = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
        
        items.add("Base Service Fee: " + formatAmount(baseAmount));
        
        if (insuranceRedesignActive && ("insurance".equals(clientType) || "premium".equals(clientType))) {
            items.add("Digital Processing Fee: $25.00");
            items.add("AI Risk Assessment: $15.00");
        }
        
        addConditionalItems(items, insuranceRedesignActive, clientType);
        
        return items;
    }
    
    // Document fee logic removed
    private void addConditionalItems(List<String> items, boolean insuranceEnabled, String clientType) {
        if (insuranceEnabled) {
            items.add("Comprehensive Service Bundle: $50.00");
        }
        
        if (insuranceEnabled && "individual".equals(clientType)) {
            items.add("Individual Enhanced Coverage: $30.00");
        }
    }
    
    private String formatAmount(BigDecimal amount) {
        return String.format("$%s", amount);
    }
}
