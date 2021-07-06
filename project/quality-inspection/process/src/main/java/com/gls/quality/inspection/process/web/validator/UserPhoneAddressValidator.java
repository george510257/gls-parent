package com.gls.quality.inspection.process.web.validator;

import com.gls.quality.inspection.process.web.model.UserPhoneAddressModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class UserPhoneAddressValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return UserPhoneAddressModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserPhoneAddressModel model = (UserPhoneAddressModel) target;
        // todo
    }
}
