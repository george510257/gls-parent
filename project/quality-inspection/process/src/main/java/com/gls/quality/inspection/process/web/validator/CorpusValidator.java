package com.gls.quality.inspection.process.web.validator;

import com.gls.quality.inspection.process.web.model.CorpusModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class CorpusValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return CorpusModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CorpusModel model = (CorpusModel) target;
        // todo
    }
}
