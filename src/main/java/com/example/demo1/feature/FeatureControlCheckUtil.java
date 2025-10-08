package com.example.demo1.feature;

import org.springframework.stereotype.Component;

@Component
public class FeatureControlCheckUtil {
    
    private static final String CQ_SET_DOC_FEE_CAPITALIZED_Y = "CQ_SET_DOC_FEE_CAPITALIZED_Y";
    private static final String EC_INSURANCE_REDESIGN = "EC_INSURANCE_REDESIGN";
    
    public static boolean isCqSetDocFeeCapitalizedWithYValueEnabled(){
        return isFeatureEnabled(CQ_SET_DOC_FEE_CAPITALIZED_Y);
    }
    
    public static boolean isFeatureEnabled(String featureId){
        return true;
    }
    
    public static boolean isEcInsuranceRedesignEnabled(){
        return isFeatureEnabled(EC_INSURANCE_REDESIGN);
    }
    
}
