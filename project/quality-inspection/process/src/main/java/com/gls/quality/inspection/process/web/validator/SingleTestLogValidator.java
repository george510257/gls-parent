package com.gls.quality.inspection.process.web.validator;

import com.gls.quality.inspection.process.web.model.SingleTestLogModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class SingleTestLogValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return SingleTestLogModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SingleTestLogModel model = (SingleTestLogModel) target;
        // todo
    }
}
