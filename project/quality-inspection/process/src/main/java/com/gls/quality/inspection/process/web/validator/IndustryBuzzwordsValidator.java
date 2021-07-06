package com.gls.quality.inspection.process.web.validator;

import com.gls.quality.inspection.process.web.model.IndustryBuzzwordsModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class IndustryBuzzwordsValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return IndustryBuzzwordsModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        IndustryBuzzwordsModel model = (IndustryBuzzwordsModel) target;
        // todo
    }
}
