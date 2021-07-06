package com.gls.quality.inspection.process.web.validator;

import com.gls.quality.inspection.process.web.model.ResultTextSplitWordCustomerModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class ResultTextSplitWordCustomerValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ResultTextSplitWordCustomerModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ResultTextSplitWordCustomerModel model = (ResultTextSplitWordCustomerModel) target;
        // todo
    }
}
