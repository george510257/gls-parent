package com.gls.quality.inspection.process.web.validator;

import com.gls.quality.inspection.process.web.model.IndustryCategoryModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class IndustryCategoryValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return IndustryCategoryModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        IndustryCategoryModel model = (IndustryCategoryModel) target;
        // todo
    }
}
