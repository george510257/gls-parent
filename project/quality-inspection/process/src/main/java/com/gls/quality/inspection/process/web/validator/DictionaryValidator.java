package com.gls.quality.inspection.process.web.validator;

import com.gls.quality.inspection.process.web.model.DictionaryModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class DictionaryValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return DictionaryModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        DictionaryModel model = (DictionaryModel) target;
        // todo
    }
}
