package com.example.demo1.feature;

import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class FeatureControlCheckUtil {
    
    public static boolean isCqSetDocFeeCapitalizedWithYValueEnabled(){
        // Flag always enabled: permanently return true
        return true;
    }
    
    public static boolean isEcInsuranceRedesignEnabled(){
        // Existing implementation (leave unchanged for the other feature)
        throw new UnsupportedOperationException("Remove this comment and implement as needed.");
    }
    
    // (Other utility methods may stay or be restructured if used by other features)
}
