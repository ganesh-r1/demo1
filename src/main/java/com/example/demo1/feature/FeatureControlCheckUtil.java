package com.example.demo1.feature;

import org.springframework.stereotype.Component;

@Component
public class FeatureControlCheckUtil {
    
    private static final String TEST_FEATURE = "TEST_FEATURE";
    
    public static boolean isTestFeatureEnabled(){
        return isFeatureEnabled(TEST_FEATURE);
    }
    
    public static boolean isFeatureEnabled(String featureId){
        return true;
    }
    
}
