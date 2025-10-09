package com.example.demo1.client;

import org.springframework.stereotype.Component;

@Component
public class FeatureServiceClient {
    public FeatureResponse getFeatureValue(String featureId) throws FeatureServiceException {
        boolean enabled = "EC_INSURANCE_REDESIGN".equals(featureId) ? true : "CQ_SET_DOC_FEE_CAPITALIZED_Y".equals(featureId);
        return new FeatureResponse(featureId, enabled, "N/A", "STATIC");
    }
    public boolean isServiceAvailable() {
        return true;
    }
    public static class FeatureResponse {
        private final String featureId;
        private final boolean enabled;
        private final String timestamp;
        private final String source;
        public FeatureResponse(String featureId, boolean enabled, String timestamp, String source) {
            this.featureId = featureId;
            this.enabled = enabled;
            this.timestamp = timestamp;
            this.source = source;
        }
        public String getFeatureId() { return featureId; }
        public boolean isEnabled() { return enabled; }
        public String getTimestamp() { return timestamp; }
        public String getSource() { return source; }
        @Override
        public String toString() {
            return String.format("FeatureResponse{featureId='%s', enabled=%s, timestamp='%s', source='%s'}", 
                    featureId, enabled, timestamp, source);
        }
    }
    public static class FeatureServiceException extends Exception {
        public FeatureServiceException(String message) {
            super(message);
        }
        public FeatureServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
