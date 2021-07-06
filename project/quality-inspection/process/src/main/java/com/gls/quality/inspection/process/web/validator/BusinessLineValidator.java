package com.gls.quality.inspection.process.web.validator;

import com.gls.quality.inspection.process.web.model.BusinessLineModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class BusinessLineValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return BusinessLineModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BusinessLineModel model = (BusinessLineModel) target;
        // todo
    }
}
