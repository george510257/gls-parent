package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.ConfigScoreSystemEntity;
import com.gls.quality.inspection.process.web.model.ConfigScoreSystemModel;
import org.springframework.stereotype.Component;

/**
 * @author george
 */
@Component
public class ConfigScoreSystemConverter implements BaseConverter<ConfigScoreSystemEntity, ConfigScoreSystemModel> {
    @Override
    public ConfigScoreSystemModel copySourceToTarget(ConfigScoreSystemEntity configScoreSystemEntity, ConfigScoreSystemModel configScoreSystemModel) {
        configScoreSystemModel.setScoreStrategy(configScoreSystemEntity.getScoreStrategy());
        configScoreSystemModel.setScoreAttribute(configScoreSystemEntity.getScoreAttribute());
        configScoreSystemModel.setDefaultScore(configScoreSystemEntity.getDefaultScore());
        configScoreSystemModel.setScoreBaseline(configScoreSystemEntity.getScoreBaseline());
        configScoreSystemModel.setId(configScoreSystemEntity.getId());
        return configScoreSystemModel;
    }

    @Override
    public ConfigScoreSystemEntity copyTargetToSource(ConfigScoreSystemModel configScoreSystemModel, ConfigScoreSystemEntity configScoreSystemEntity) {
        configScoreSystemEntity.setScoreStrategy(configScoreSystemModel.getScoreStrategy());
        configScoreSystemEntity.setScoreAttribute(configScoreSystemModel.getScoreAttribute());
        configScoreSystemEntity.setDefaultScore(configScoreSystemModel.getDefaultScore());
        configScoreSystemEntity.setScoreBaseline(configScoreSystemModel.getScoreBaseline());
        configScoreSystemEntity.setId(configScoreSystemModel.getId());
        return configScoreSystemEntity;
    }
}