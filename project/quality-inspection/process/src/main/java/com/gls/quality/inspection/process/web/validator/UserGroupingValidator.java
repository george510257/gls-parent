package com.gls.quality.inspection.process.web.validator;

import com.gls.quality.inspection.process.web.model.UserGroupingModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class UserGroupingValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return UserGroupingModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserGroupingModel model = (UserGroupingModel) target;
        // todo
    }
}
