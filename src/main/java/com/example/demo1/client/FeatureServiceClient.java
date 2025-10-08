package com.example.demo1.client;

import org.springframework.stereotype.Component;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.time.Duration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

@Component
public class FeatureServiceClient {
    
    private static final String FEATURE_TEST_ENDPOINT = "http://localhost:8080/api/test/feature";
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(5);
    
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    
    public FeatureServiceClient() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(REQUEST_TIMEOUT)
                .build();
        this.objectMapper = new ObjectMapper();
    }
    
    public FeatureResponse getFeatureValue(String featureId) throws FeatureServiceException {
        // CQ_SET_DOC_FEE_CAPITALIZED_Y is always enabled - never do network call for it
        if ("CQ_SET_DOC_FEE_CAPITALIZED_Y".equals(featureId)) {
            return new FeatureResponse(featureId, true, String.valueOf(System.currentTimeMillis()), "STATIC_ALWAYS_ENABLED");
        }
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(FEATURE_TEST_ENDPOINT + "?featureId=" + featureId))
                    .timeout(REQUEST_TIMEOUT)
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .GET()
                    .build();
            
            HttpResponse<String> response = httpClient.send(request, 
                    HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                return parseFeatureResponse(response.body(), featureId);
            } else {
                throw new FeatureServiceException(
                    "Feature endpoint returned status: " + response.statusCode() + 
                    " for feature: " + featureId
                );
            }
            
        } catch (Exception e) {
            throw new FeatureServiceException("Error fetching feature " + featureId, e);
        }
    }
    
    private FeatureResponse parseFeatureResponse(String responseBody, String featureId) 
            throws FeatureServiceException {
        try {
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            boolean enabled = jsonNode.path("enabled").asBoolean(false);
            String timestamp = jsonNode.path("timestamp").asText();
            String source = jsonNode.path("source").asText("UNKNOWN");
            
            return new FeatureResponse(featureId, enabled, timestamp, source);
            
        } catch (Exception e) {
            throw new FeatureServiceException("Failed to parse feature response for " + featureId, e);
        }
    }
    
    public boolean isServiceAvailable() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(FEATURE_TEST_ENDPOINT + "/health"))
                    .timeout(Duration.ofSeconds(2))
                    .GET()
                    .build();
            
            HttpResponse<String> response = httpClient.send(request, 
                    HttpResponse.BodyHandlers.ofString());
            
            return response.statusCode() == 200;
            
        } catch (Exception e) {
            return false;
        }
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
