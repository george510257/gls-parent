package com.gls.quality.inspection.process.web.validator;

import com.gls.quality.inspection.process.web.model.SpotCheckRuleModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class SpotCheckRuleValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return SpotCheckRuleModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SpotCheckRuleModel model = (SpotCheckRuleModel) target;
        // todo
    }
}
