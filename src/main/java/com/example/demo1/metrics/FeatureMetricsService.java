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
    // REMOVE: private final AtomicLong insuranceUsageCounter = new AtomicLong(0);
    // REMOVE: private final AtomicLong combinedUsageCounter = new AtomicLong(0);
    
    public void recordFeatureUsage(String operationType) {
        // Capture feature states at time of usage
        boolean docFeeActive = FeatureControlCheckUtil.isCqSetDocFeeCapitalizedWithYValueEnabled();
        // REMOVE: boolean insuranceActive = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
        
        // Pass feature states to tracking methods
        trackFeatureUsage(operationType, docFeeActive);
        updateCounters(docFeeActive);
    }
    
    // REMOVE: private void trackFeatureUsage(String operation, boolean docFeeEnabled, boolean insuranceEnabled) { ... }
    private void trackFeatureUsage(String operation, boolean docFeeEnabled) {
        Map<String, Object> usageEvent = new HashMap<>();
        usageEvent.put("timestamp", LocalDateTime.now());
        usageEvent.put("operation", operation);
        usageEvent.put("cq_set_doc_fee_capitalized_y", docFeeEnabled);
        logUsageEvent(usageEvent);
    }
    
    // REMOVE: private void updateCounters(boolean docFeeEnabled, boolean insuranceEnabled) { ... }
    private void updateCounters(boolean docFeeEnabled) {
        if (docFeeEnabled) {
            docFeeUsageCounter.incrementAndGet();
        }
    }
    
    public Map<String, Object> generateMetricsReport() {
        Map<String, Object> report = new HashMap<>();
        
        // Get current feature states for report
        boolean currentDocFeeState = FeatureControlCheckUtil.isCqSetDocFeeCapitalizedWithYValueEnabled();
        // REMOVE: boolean currentInsuranceState = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
        
        report.put("report_timestamp", LocalDateTime.now());
        report.put("current_doc_fee_state", currentDocFeeState);
        // REMOVE: report.put("current_insurance_state", currentInsuranceState);
        report.put("doc_fee_usage_count", docFeeUsageCounter.get());
        // REMOVE: insurance_usage_count, combined_usage_count, derived metrics
        // REMOVE: long totalUsage = docFeeUsageCounter.get() + insuranceUsageCounter.get();
        report.put("total_feature_usage", docFeeUsageCounter.get());
        return report;
    }
    
    public boolean isHighUsageScenario() {
        boolean docFeeEnabled = FeatureControlCheckUtil.isCqSetDocFeeCapitalizedWithYValueEnabled();
        // REMOVE: boolean insuranceEnabled = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
        return analyzeUsagePattern(docFeeEnabled);
    }
    
    // REMOVE: private boolean analyzeUsagePattern(boolean docFeeActive, boolean insuranceActive) { ... }
    private boolean analyzeUsagePattern(boolean docFeeActive) {
        long totalUsage = docFeeUsageCounter.get();
        return totalUsage > 200;
    }
    
    private void logUsageEvent(Map<String, Object> event) {
        System.out.println("Feature Usage Event: " + event);
    }
}
