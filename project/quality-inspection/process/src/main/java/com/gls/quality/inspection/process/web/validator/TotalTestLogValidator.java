package com.gls.quality.inspection.process.web.validator;

import com.gls.quality.inspection.process.web.model.TotalTestLogModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class TotalTestLogValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return TotalTestLogModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TotalTestLogModel model = (TotalTestLogModel) target;
        // todo
    }
}
