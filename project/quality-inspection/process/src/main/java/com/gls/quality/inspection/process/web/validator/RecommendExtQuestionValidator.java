package com.gls.quality.inspection.process.web.validator;

import com.gls.quality.inspection.process.web.model.RecommendExtQuestionModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class RecommendExtQuestionValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return RecommendExtQuestionModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RecommendExtQuestionModel model = (RecommendExtQuestionModel) target;
        // todo
    }
}
