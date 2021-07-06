package com.gls.quality.inspection.process.web.validator;

import com.gls.quality.inspection.process.web.model.ConfigScoreSystemModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class ConfigScoreSystemValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ConfigScoreSystemModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ConfigScoreSystemModel model = (ConfigScoreSystemModel) target;
        // todo
    }
}
