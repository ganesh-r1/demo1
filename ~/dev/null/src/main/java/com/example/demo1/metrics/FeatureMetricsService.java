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
        boolean docFeeActive = FeatureControlCheckUtil.isCqSetDocFeeCapitalizedWithYValueEnabled();
        
        trackFeatureUsage(operationType, docFeeActive);
        updateCounters(docFeeActive);
    }
    
    private void trackFeatureUsage(String operation, boolean docFeeEnabled) {
        Map<String, Object> usageEvent = new HashMap<>();
        usageEvent.put("timestamp", LocalDateTime.now());
        usageEvent.put("operation", operation);
        usageEvent.put("cq_set_doc_fee_capitalized_y", docFeeEnabled);
        
        if (docFeeEnabled) {
            usageEvent.put("feature_combination", "DOC_FEE_ONLY");
        } else {
            usageEvent.put("feature_combination", "NONE_ENABLED");
        }
        
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
        
        report.put("report_timestamp", LocalDateTime.now());
        report.put("current_doc_fee_state", currentDocFeeState);
        report.put("doc_fee_usage_count", docFeeUsageCounter.get());
        report.put("insurance_usage_count", insuranceUsageCounter.get());
        report.put("combined_usage_count", combinedUsageCounter.get());
        
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
        boolean docFeeEnabled = FeatureControlCheckUtil.isCqSetDocFeeCapitalizedWithYValueEnabled();
        
        return analyzeUsagePattern(docFeeEnabled);
    }
    
    private boolean analyzeUsagePattern(boolean docFeeActive) {
        long totalUsage = docFeeUsageCounter.get() + insuranceUsageCounter.get();
        
        if (docFeeActive) {
            return totalUsage > 200;
        }
        
        return totalUsage > 200;
    }
    
    private void logUsageEvent(Map<String, Object> event) {
        System.out.println("Feature Usage Event: " + event);
    }
}