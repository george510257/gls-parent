package com.gls.common.user.web.validator;

import com.gls.common.user.api.model.ClientModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class ClientValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ClientModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ClientModel model = (ClientModel) target;
        // todo
    }
}
