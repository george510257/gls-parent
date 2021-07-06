package com.gls.quality.inspection.process.web.validator;

import com.gls.quality.inspection.process.web.model.CorpusAudioTranforModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author george
 */
@Component
public class CorpusAudioTranforValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return CorpusAudioTranforModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CorpusAudioTranforModel model = (CorpusAudioTranforModel) target;
        // todo
    }
}
