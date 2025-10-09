package com.example.demo1.metrics;

import com.example.demo1.feature.FeatureControlCheckUtil;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.time.LocalDateTime;

@Service
public class FeatureMetricsService {
    
    private final AtomicLong docFeeUsageCounter = new AtomicLong(0);
    // private final AtomicLong insuranceUsageCounter = new AtomicLong(0); // REMOVED
    // private final AtomicLong combinedUsageCounter = new AtomicLong(0); // REMOVED
    
    public void recordFeatureUsage(String operationType) {
        boolean docFeeActive = FeatureControlCheckUtil.isCqSetDocFeeCapitalizedWithYValueEnabled();
        // boolean insuranceActive = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled(); // REMOVED
        
        trackFeatureUsage(operationType, docFeeActive);
        updateCounters(docFeeActive);
    }
    
    private void trackFeatureUsage(String operation, boolean docFeeEnabled) {
        Map<String, Object> usageEvent = new HashMap<>();
        usageEvent.put("timestamp", LocalDateTime.now());
        usageEvent.put("operation", operation);
        usageEvent.put("cq_set_doc_fee_capitalized_y", docFeeEnabled);
        logUsageEvent(usageEvent);
    }
    
    private void updateCounters(boolean docFeeEnabled) {
        if (docFeeEnabled) {
            docFeeUsageCounter.incrementAndGet();
        }
    }
    
    public Map<String, Object> generateMetricsReport() {
        Map<String, Object> report = new HashMap<>();
        boolean currentDocFeeState = FeatureControlCheckUtil.isCqSetDocFeeCapitalizedWithYValueEnabled();
        // boolean currentInsuranceState = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled(); // REMOVED
        report.put("report_timestamp", LocalDateTime.now());
        report.put("current_doc_fee_state", currentDocFeeState);
        // report.put("current_insurance_state", currentInsuranceState); // REMOVED
        report.put("doc_fee_usage_count", docFeeUsageCounter.get());
        // report.put("insurance_usage_count", insuranceUsageCounter.get()); // REMOVED
        // report.put("combined_usage_count", combinedUsageCounter.get()); // REMOVED
        long totalUsage = docFeeUsageCounter.get();
        report.put("total_feature_usage", totalUsage);
        return report;
    }
    
    public boolean isHighUsageScenario() {
        boolean docFeeEnabled = FeatureControlCheckUtil.isCqSetDocFeeCapitalizedWithYValueEnabled();
        // boolean insuranceEnabled = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled(); // REMOVED
        return analyzeUsagePattern(docFeeEnabled);
    }
    
    private boolean analyzeUsagePattern(boolean docFeeActive) {
        long totalUsage = docFeeUsageCounter.get();
        return totalUsage > 200;
    }
    
    private void logUsageEvent(Map<String, Object> event) {
        System.out.println("Feature Usage Event: " + event);
    }
}
