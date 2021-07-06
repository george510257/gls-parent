package com.gls.quality.inspection.process.web.validator;

import com.gls.quality.inspection.process.web.model.IntentionAnalysisModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class IntentionAnalysisValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return IntentionAnalysisModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        IntentionAnalysisModel model = (IntentionAnalysisModel) target;
        // todo
    }
}
