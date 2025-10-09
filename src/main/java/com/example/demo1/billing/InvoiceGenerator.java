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
        // REMOVE: capitalizedFeeActive flag and logic
        boolean insuranceRedesignActive = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
        items.add("Base Service Fee: " + formatAmount(baseAmount, false));
        // REMOVE: Enhanced Fee (Y-Capitalized)
        if (insuranceRedesignActive && ("insurance".equals(clientType) || "premium".equals(clientType))) {
            items.add("Digital Processing Fee: $25.00");
            items.add("AI Risk Assessment: $15.00");
        }
        addConditionalItems(items, false, insuranceRedesignActive, clientType);
        return items;
    }
    
    private void addConditionalItems(List<String> items, boolean docFeeEnabled, 
                                   boolean insuranceEnabled, String clientType) {
        if (docFeeEnabled && insuranceEnabled) {
            // REMOVE: Comprehensive Service Bundle
        }
        if (docFeeEnabled && "corporate".equals(clientType)) {
            // REMOVE: Corporate Documentation Fee
        }
        if (insuranceEnabled && "individual".equals(clientType)) {
            items.add("Individual Enhanced Coverage: $30.00");
        }
    }
    
    private String formatAmount(BigDecimal amount, boolean useCapitalizedFormat) {
        return String.format("$%s", amount);
    }
}
