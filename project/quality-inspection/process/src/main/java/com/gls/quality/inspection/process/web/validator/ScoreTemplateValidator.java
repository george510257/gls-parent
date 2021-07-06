package com.gls.quality.inspection.process.web.validator;

import com.gls.quality.inspection.process.web.model.ScoreTemplateModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class ScoreTemplateValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ScoreTemplateModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ScoreTemplateModel model = (ScoreTemplateModel) target;
        // todo
    }
}
