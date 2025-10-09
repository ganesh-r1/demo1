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
        // CQ_SET_DOC_FEE_CAPITALIZED_Y always enabled, so only print enabled
        System.out.println("Test Feature is enabled");
    } 
    
}
