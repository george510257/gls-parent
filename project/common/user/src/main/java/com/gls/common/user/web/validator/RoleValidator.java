package com.gls.common.user.web.validator;

import com.gls.common.user.api.model.RoleModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class RoleValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return RoleModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RoleModel model = (RoleModel) target;
        // todo
    }
}
