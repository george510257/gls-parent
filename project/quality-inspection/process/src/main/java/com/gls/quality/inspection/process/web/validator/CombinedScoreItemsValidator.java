package com.gls.quality.inspection.process.web.validator;

import com.gls.quality.inspection.process.web.model.CombinedScoreItemsModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class CombinedScoreItemsValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return CombinedScoreItemsModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CombinedScoreItemsModel model = (CombinedScoreItemsModel) target;
        // todo
    }
}
