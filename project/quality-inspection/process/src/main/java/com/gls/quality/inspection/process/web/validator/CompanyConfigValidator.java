package com.gls.quality.inspection.process.web.validator;

import com.gls.quality.inspection.process.web.model.CompanyConfigModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class CompanyConfigValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return CompanyConfigModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CompanyConfigModel model = (CompanyConfigModel) target;
        // todo
    }
}
