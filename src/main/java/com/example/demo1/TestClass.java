package com.example.demo1;

import com.example.demo1.feature.FeatureControlCheckUtil;
import org.springframework.stereotype.Component;

@Component
public class TestClass {
    
    private final FeatureControlCheckUtil featureControlCheckUtil;
    
    public TestClass(FeatureControlCheckUtil featureControlCheckUtil){
        this.featureControlCheckUtil = featureControlCheckUtil;
    }
    
    
    public void testFunction(){
        // Always print enabled since the flag is now always enabled
        System.out.println("Test Feature is enabled");    
    } 
    
}
