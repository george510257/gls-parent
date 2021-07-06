package com.gls.quality.inspection.process.web.validator;

import com.gls.quality.inspection.process.web.model.LabelTestDataModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class LabelTestDataValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return LabelTestDataModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LabelTestDataModel model = (LabelTestDataModel) target;
        // todo
    }
}
