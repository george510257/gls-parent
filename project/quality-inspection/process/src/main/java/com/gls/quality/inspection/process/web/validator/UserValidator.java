package com.gls.quality.inspection.process.web.validator;

import com.gls.quality.inspection.process.web.model.UserModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return UserModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserModel model = (UserModel) target;
        // todo
    }
}
