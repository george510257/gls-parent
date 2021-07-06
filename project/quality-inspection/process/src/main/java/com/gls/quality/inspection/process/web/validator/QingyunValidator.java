package com.gls.quality.inspection.process.web.validator;

import com.gls.quality.inspection.process.web.model.QingyunModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class QingyunValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return QingyunModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        QingyunModel model = (QingyunModel) target;
        // todo
    }
}
