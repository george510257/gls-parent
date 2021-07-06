package com.gls.quality.inspection.process.web.validator;

import com.gls.quality.inspection.process.web.model.ResultTextSplitWordModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class ResultTextSplitWordValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ResultTextSplitWordModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ResultTextSplitWordModel model = (ResultTextSplitWordModel) target;
        // todo
    }
}
