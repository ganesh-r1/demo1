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
        boolean capitalizedFeeActive = FeatureControlCheckUtil.isCqSetDocFeeCapitalizedWithYValueEnabled();
        items.add("Base Service Fee: " + formatAmount(baseAmount, capitalizedFeeActive));
        if (capitalizedFeeActive && "premium".equals(clientType)) {
            BigDecimal enhancedFee = baseAmount.multiply(new BigDecimal("0.12"));
            items.add("Enhanced Fee (Y-Capitalized): " + formatAmount(enhancedFee, true));
        }
        // Removed insuranceRedesignActive logic/conditions and insurance-dependent items
        addConditionalItems(items, capitalizedFeeActive, clientType);
        return items;
    }
    private void addConditionalItems(List<String> items, boolean docFeeEnabled, String clientType) {
        if (docFeeEnabled && "corporate".equals(clientType)) {
            items.add("Corporate Documentation Fee: $75.00");
        }
    }
    private String formatAmount(BigDecimal amount, boolean useCapitalizedFormat) {
        if (useCapitalizedFormat) {
            return String.format("$%s (Y-CAPITALIZED)", amount);
        }
        return String.format("$%s", amount);
    }
}
