package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.ConfigWordFilterEntity;
import com.gls.quality.inspection.process.web.model.ConfigWordFilterModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class ConfigWordFilterConverter implements BaseConverter<ConfigWordFilterEntity, ConfigWordFilterModel> {
    @Override
    public ConfigWordFilterModel copySourceToTarget(ConfigWordFilterEntity configWordFilterEntity, ConfigWordFilterModel configWordFilterModel) {
        configWordFilterModel.setServiceWordFilter(configWordFilterEntity.getServiceWordFilter());
        configWordFilterModel.setCustomerWordFilter(configWordFilterEntity.getCustomerWordFilter());
        configWordFilterModel.setId(configWordFilterEntity.getId());
        return configWordFilterModel;
    }

    @Override
    public ConfigWordFilterEntity copyTargetToSource(ConfigWordFilterModel configWordFilterModel, ConfigWordFilterEntity configWordFilterEntity) {
        configWordFilterEntity.setServiceWordFilter(configWordFilterModel.getServiceWordFilter());
        configWordFilterEntity.setCustomerWordFilter(configWordFilterModel.getCustomerWordFilter());
        configWordFilterEntity.setId(configWordFilterModel.getId());
        return configWordFilterEntity;
    }
}