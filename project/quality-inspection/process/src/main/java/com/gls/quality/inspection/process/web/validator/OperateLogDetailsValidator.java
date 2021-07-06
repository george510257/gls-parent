package com.gls.quality.inspection.process.web.validator;

import com.gls.quality.inspection.process.web.model.OperateLogDetailsModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class OperateLogDetailsValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return OperateLogDetailsModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        OperateLogDetailsModel model = (OperateLogDetailsModel) target;
        // todo
    }
}
