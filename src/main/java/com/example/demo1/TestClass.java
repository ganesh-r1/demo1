package com.example.demo1;

import org.springframework.stereotype.Component;

@Component
public class TestClass {
    
    public TestClass(){}
    
    public void testFunction(){
        // CQ_SET_DOC_FEE_CAPITALIZED_Y is always enabled after feature flag removal
        System.out.println("Test Feature is enabled");   
    } 
    
}
