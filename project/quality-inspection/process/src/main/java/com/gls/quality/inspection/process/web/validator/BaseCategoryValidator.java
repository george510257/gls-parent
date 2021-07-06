package com.gls.quality.inspection.process.web.validator;

import com.gls.quality.inspection.process.web.model.BaseCategoryModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class BaseCategoryValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return BaseCategoryModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BaseCategoryModel model = (BaseCategoryModel) target;
        // todo
    }
}
