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
    // private final AtomicLong insuranceUsageCounter = new AtomicLong(0); // removed
    // private final AtomicLong combinedUsageCounter = new AtomicLong(0); // removed
    
    public void recordFeatureUsage(String operationType) {
        // Capture feature states at time of usage
        boolean docFeeActive = FeatureControlCheckUtil.isCqSetDocFeeCapitalizedWithYValueEnabled();
        // boolean insuranceActive = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled(); // removed
        
        // Pass feature states to tracking methods
        trackFeatureUsage(operationType, docFeeActive);
        updateCounters(docFeeActive);
    }
    
    private void trackFeatureUsage(String operation, boolean docFeeEnabled) {
        Map<String, Object> usageEvent = new HashMap<>();
        usageEvent.put("timestamp", LocalDateTime.now());
        usageEvent.put("operation", operation);
        usageEvent.put("cq_set_doc_fee_capitalized_y", docFeeEnabled);
        // usageEvent.put("ec_insurance_redesign", insuranceEnabled); // removed
        
        logUsageEvent(usageEvent);
    }
    
    private void updateCounters(boolean docFeeEnabled) {
        if (docFeeEnabled) {
            docFeeUsageCounter.incrementAndGet();
        }
        // if (insuranceEnabled) { insuranceUsageCounter.incrementAndGet(); } // removed
    }
    
    public Map<String, Object> generateMetricsReport() {
        Map<String, Object> report = new HashMap<>();
        
        // Get current feature states for report
        boolean currentDocFeeState = FeatureControlCheckUtil.isCqSetDocFeeCapitalizedWithYValueEnabled();
        // boolean currentInsuranceState = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled(); // removed
        
        report.put("report_timestamp", LocalDateTime.now());
        report.put("current_doc_fee_state", currentDocFeeState);
        // report.put("current_insurance_state", currentInsuranceState); // removed
        report.put("doc_fee_usage_count", docFeeUsageCounter.get());
        // report.put("insurance_usage_count", insuranceUsageCounter.get()); // removed
        // report.put("combined_usage_count", combinedUsageCounter.get()); // removed
        
        // Calculate derived metrics
        long totalUsage = docFeeUsageCounter.get();
        report.put("total_feature_usage", totalUsage);
        
        // if (totalUsage > 0) { ... } // removed insurance percentage
        
        return report;
    }
    
    public boolean isHighUsageScenario() {
        boolean docFeeEnabled = FeatureControlCheckUtil.isCqSetDocFeeCapitalizedWithYValueEnabled();
        // boolean insuranceEnabled = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled(); // removed
        
        return analyzeUsagePattern(docFeeEnabled);
    }
    
    private boolean analyzeUsagePattern(boolean docFeeActive) {
        long totalUsage = docFeeUsageCounter.get();
        
        if (docFeeActive) {
            return totalUsage > 100;
        }
        
        return totalUsage > 200;
    }
    
    private void logUsageEvent(Map<String, Object> event) {
        System.out.println("Feature Usage Event: " + event);
    }
}
