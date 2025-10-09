package com.example.demo1.processor;

import com.example.demo1.feature.FeatureControlCheckUtil;
import org.springframework.stereotype.Component;

@Component
public class DocumentProcessor {
    public void processDocument(String documentId) {
        boolean useCapitalizedFeeFormat = FeatureControlCheckUtil.isCqSetDocFeeCapitalizedWithYValueEnabled();
        String processingMode = determineProcessingMode(useCapitalizedFeeFormat);
        System.out.println("Processing document " + documentId + " with mode: " + processingMode);
        if (useCapitalizedFeeFormat) {
            applyCapitalizedFeeLogic(documentId);
        }
    }
    private String determineProcessingMode(boolean capitalizedFee) {
        if (capitalizedFee) {
            return "CAPITALIZED_Y_MODE";
        }
        return "STANDARD_MODE";
    }
    private void applyCapitalizedFeeLogic(String documentId) {
        System.out.println("Applying capitalized fee logic for: " + documentId);
    }
}
