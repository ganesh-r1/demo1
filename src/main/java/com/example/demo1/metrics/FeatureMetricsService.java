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
    // REMOVED: private final AtomicLong insuranceUsageCounter = new AtomicLong(0);
    private final AtomicLong combinedUsageCounter = new AtomicLong(0);
    
    public void recordFeatureUsage(String operationType) {
        // Capture feature states at time of usage
        boolean docFeeActive = FeatureControlCheckUtil.isCqSetDocFeeCapitalizedWithYValueEnabled();
        // REMOVED: boolean insuranceActive = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
        boolean insuranceActive = false;
        
        // Pass feature states to tracking methods
        trackFeatureUsage(operationType, docFeeActive, insuranceActive);
        updateCounters(docFeeActive);
    }
    
    private void trackFeatureUsage(String operation, boolean docFeeEnabled, boolean insuranceEnabled) {
        Map<String, Object> usageEvent = new HashMap<>();
        usageEvent.put("timestamp", LocalDateTime.now());
        usageEvent.put("operation", operation);
        usageEvent.put("cq_set_doc_fee_capitalized_y", docFeeEnabled);
        usageEvent.put("ec_insurance_redesign", insuranceEnabled);
        
        logUsageEvent(usageEvent);
    }
    
    // updateCounters only increments docFee now
    private void updateCounters(boolean docFeeEnabled) {
        if (docFeeEnabled) {
            docFeeUsageCounter.incrementAndGet();
        }
    }
    
    public Map<String, Object> generateMetricsReport() {
        Map<String, Object> report = new HashMap<>();
        
        // Get current feature states for report
        boolean currentDocFeeState = FeatureControlCheckUtil.isCqSetDocFeeCapitalizedWithYValueEnabled();
        boolean currentInsuranceState = false;
        
        report.put("report_timestamp", LocalDateTime.now());
        report.put("current_doc_fee_state", currentDocFeeState);
        report.put("current_insurance_state", currentInsuranceState);
        report.put("doc_fee_usage_count", docFeeUsageCounter.get());
        // REMOVED: report.put("insurance_usage_count", insuranceUsageCounter.get());
        report.put("combined_usage_count", combinedUsageCounter.get());
        
        // Calculate derived metrics
        long totalUsage = docFeeUsageCounter.get();
        report.put("total_feature_usage", totalUsage);
        
        if (totalUsage > 0) {
            double docFeePercentage = 100.0;
            double insurancePercentage = 0.0;
            
            report.put("doc_fee_usage_percentage", docFeePercentage);
            report.put("insurance_usage_percentage", insurancePercentage);
        }
        
        return report;
    }
    
    public boolean isHighUsageScenario() {
        boolean docFeeEnabled = FeatureControlCheckUtil.isCqSetDocFeeCapitalizedWithYValueEnabled();
        boolean insuranceEnabled = false;
        
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
