package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.SpotCheckRuleEntity;
import com.gls.quality.inspection.process.web.model.SpotCheckRuleModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class SpotCheckRuleConverter implements BaseConverter<SpotCheckRuleEntity, SpotCheckRuleModel> {
    @Override
    public SpotCheckRuleModel copySourceToTarget(SpotCheckRuleEntity entity, SpotCheckRuleModel model) {
        // todo
        return model;
    }

    @Override
    public SpotCheckRuleEntity copyTargetToSource(SpotCheckRuleModel model, SpotCheckRuleEntity entity) {
        // todo
        return entity;
    }
}
