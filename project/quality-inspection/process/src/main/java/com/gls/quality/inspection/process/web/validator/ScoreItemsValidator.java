package com.gls.quality.inspection.process.web.validator;

import com.gls.quality.inspection.process.web.model.ScoreItemsModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class ScoreItemsValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ScoreItemsModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ScoreItemsModel model = (ScoreItemsModel) target;
        // todo
    }
}
