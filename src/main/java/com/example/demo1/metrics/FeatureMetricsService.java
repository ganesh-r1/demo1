package com.example.demo1.metrics;

import com.example.demo1.feature.FeatureControlCheckUtil;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.time.LocalDateTime;

@Service
public class FeatureMetricsService {
    
    private final AtomicLong insuranceUsageCounter = new AtomicLong(0);
    private final AtomicLong combinedUsageCounter = new AtomicLong(0);
    
    public void recordFeatureUsage(String operationType) {
        boolean insuranceActive = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
        trackFeatureUsage(operationType, insuranceActive);
        updateCounters(insuranceActive);
    }
    
    private void trackFeatureUsage(String operation, boolean insuranceEnabled) {
        Map<String, Object> usageEvent = new HashMap<>();
        usageEvent.put("timestamp", LocalDateTime.now());
        usageEvent.put("operation", operation);
        usageEvent.put("ec_insurance_redesign", insuranceEnabled);
        logUsageEvent(usageEvent);
    }
    
    private void updateCounters(boolean insuranceEnabled) {
        if (insuranceEnabled) {
            insuranceUsageCounter.incrementAndGet();
        }
    }
    
    public Map<String, Object> generateMetricsReport() {
        Map<String, Object> report = new HashMap<>();
        boolean currentInsuranceState = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
        report.put("report_timestamp", LocalDateTime.now());
        report.put("current_insurance_state", currentInsuranceState);
        report.put("insurance_usage_count", insuranceUsageCounter.get());
        report.put("combined_usage_count", combinedUsageCounter.get());
        long totalUsage = insuranceUsageCounter.get();
        report.put("total_feature_usage", totalUsage);
        if (totalUsage > 0) {
            double insurancePercentage = (insuranceUsageCounter.get() * 100.0) / totalUsage;
            report.put("insurance_usage_percentage", insurancePercentage);
        }
        return report;
    }
    
    public boolean isHighUsageScenario() {
        boolean insuranceEnabled = FeatureControlCheckUtil.isEcInsuranceRedesignEnabled();
        return analyzeUsagePattern(insuranceEnabled);
    }
    
    private boolean analyzeUsagePattern(boolean insuranceActive) {
        long totalUsage = insuranceUsageCounter.get();
        return totalUsage > 200;
    }
    
    private void logUsageEvent(Map<String, Object> event) {
        System.out.println("Feature Usage Event: " + event);
    }
}
