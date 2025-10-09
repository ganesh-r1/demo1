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
        boolean insuranceRedesignActive = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
        items.add("Base Service Fee: " + formatAmount(baseAmount));
        // Always treat fee as capitalized
        if ("premium".equals(clientType)) {
            BigDecimal enhancedFee = baseAmount.multiply(new BigDecimal("0.12"));
            items.add("Enhanced Fee (Y-Capitalized): " + formatAmount(enhancedFee));
        }
        if (insuranceRedesignActive && ("insurance".equals(clientType) || "premium".equals(clientType))) {
            items.add("Digital Processing Fee: $25.00");
            items.add("AI Risk Assessment: $15.00");
        }
        addConditionalItems(items, insuranceRedesignActive, clientType);
        return items;
    }
    private void addConditionalItems(List<String> items, boolean insuranceEnabled, String clientType) {
        // Remove docFeeEnabled logic; always treat it as enabled
        if (insuranceEnabled) {
            items.add("Comprehensive Service Bundle: $50.00");
        }
        if ("corporate".equals(clientType)) {
            items.add("Corporate Documentation Fee: $75.00");
        }
        if (insuranceEnabled && "individual".equals(clientType)) {
            items.add("Individual Enhanced Coverage: $30.00");
        }
    }
    private String formatAmount(BigDecimal amount) {
        return String.format("$%s (Y-CAPITALIZED)", amount);
    }
}
