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
    private final AtomicLong insuranceUsageCounter = new AtomicLong(0);
    private final AtomicLong combinedUsageCounter = new AtomicLong(0);
    
    public void recordFeatureUsage(String operationType) {
        // Feature CQ_SET_DOC_FEE_CAPITALIZED_Y is removed. Always enabled.
        boolean docFeeActive = true;
        boolean insuranceActive = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
        
        // Pass feature states to tracking methods
        trackFeatureUsage(operationType, docFeeActive, insuranceActive);
        updateCounters(docFeeActive, insuranceActive);
    }
    
    private void trackFeatureUsage(String operation, boolean docFeeEnabled, boolean insuranceEnabled) {
        Map<String, Object> usageEvent = new HashMap<>();
        usageEvent.put("timestamp", LocalDateTime.now());
        usageEvent.put("operation", operation);
        usageEvent.put("cq_set_doc_fee_capitalized_y", docFeeEnabled);
        usageEvent.put("ec_insurance_redesign", insuranceEnabled);
        
        logUsageEvent(usageEvent);
    }
    
    private void updateCounters(boolean docFeeEnabled, boolean insuranceEnabled) {
        if (docFeeEnabled) {
            docFeeUsageCounter.incrementAndGet();
        }
        
        if (insuranceEnabled) {
            insuranceUsageCounter.incrementAndGet();
        }
    }
    
    public Map<String, Object> generateMetricsReport() {
        Map<String, Object> report = new HashMap<>();
        
        // Feature CQ_SET_DOC_FEE_CAPITALIZED_Y is removed. Always enabled.
        boolean currentDocFeeState = true;
        boolean currentInsuranceState = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
        
        report.put("report_timestamp", LocalDateTime.now());
        report.put("current_doc_fee_state", currentDocFeeState);
        report.put("current_insurance_state", currentInsuranceState);
        report.put("doc_fee_usage_count", docFeeUsageCounter.get());
        report.put("insurance_usage_count", insuranceUsageCounter.get());
        report.put("combined_usage_count", combinedUsageCounter.get());
        
        // Calculate derived metrics
        long totalUsage = docFeeUsageCounter.get() + insuranceUsageCounter.get();
        report.put("total_feature_usage", totalUsage);
        
        if (totalUsage > 0) {
            double docFeePercentage = (docFeeUsageCounter.get() * 100.0) / totalUsage;
            double insurancePercentage = (insuranceUsageCounter.get() * 100.0) / totalUsage;
            
            report.put("doc_fee_usage_percentage", docFeePercentage);
            report.put("insurance_usage_percentage", insurancePercentage);
        }
        
        return report;
    }
    
    public boolean isHighUsageScenario() {
        // Feature CQ_SET_DOC_FEE_CAPITALIZED_Y is removed. Always enabled.
        boolean docFeeEnabled = true;
        boolean insuranceEnabled = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
        
        return analyzeUsagePattern(docFeeEnabled, insuranceEnabled);
    }
    
    private boolean analyzeUsagePattern(boolean docFeeActive, boolean insuranceActive) {
        long totalUsage = docFeeUsageCounter.get() + insuranceUsageCounter.get();
        
        if (docFeeActive && insuranceActive) {
            return totalUsage > 100 && combinedUsageCounter.get() > 50;
        }
        
        return totalUsage > 200;
    }
    
    private void logUsageEvent(Map<String, Object> event) {
        System.out.println("Feature Usage Event: " + event);
    }
}
