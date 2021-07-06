package com.gls.quality.inspection.process.web.validator;

import com.gls.quality.inspection.process.web.model.CompanyModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class CompanyValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return CompanyModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CompanyModel model = (CompanyModel) target;
        // todo
    }
}
