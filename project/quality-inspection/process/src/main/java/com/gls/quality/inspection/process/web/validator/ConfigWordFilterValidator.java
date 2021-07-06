package com.gls.quality.inspection.process.web.validator;

import com.gls.quality.inspection.process.web.model.ConfigWordFilterModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class ConfigWordFilterValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ConfigWordFilterModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ConfigWordFilterModel model = (ConfigWordFilterModel) target;
        // todo
    }
}
