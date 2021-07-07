package com.gls.quality.inspection.process.web.validator;

import com.gls.quality.inspection.process.web.model.LoanModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class LoanValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return LoanModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LoanModel model = (LoanModel) target;
        // todo
    }
}