package com.gls.quality.inspection.process.web.validator;

import com.gls.quality.inspection.process.web.model.ExtractCheckResultModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class ExtractCheckResultValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ExtractCheckResultModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ExtractCheckResultModel model = (ExtractCheckResultModel) target;
        // todo
    }
}
