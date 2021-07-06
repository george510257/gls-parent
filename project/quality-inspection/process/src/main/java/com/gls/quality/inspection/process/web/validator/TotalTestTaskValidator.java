package com.gls.quality.inspection.process.web.validator;

import com.gls.quality.inspection.process.web.model.TotalTestTaskModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class TotalTestTaskValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return TotalTestTaskModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TotalTestTaskModel model = (TotalTestTaskModel) target;
        // todo
    }
}
