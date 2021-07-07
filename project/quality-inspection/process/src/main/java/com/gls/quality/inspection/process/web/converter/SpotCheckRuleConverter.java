package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.SpotCheckRuleEntity;
import com.gls.quality.inspection.process.web.model.SpotCheckRuleModel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class SpotCheckRuleConverter implements BaseConverter<SpotCheckRuleEntity, SpotCheckRuleModel> {
    @Resource
    private SpotCheckConverter spotCheckConverter;
    @Resource
    private ScoreItemsConverter scoreItemsConverter;

    @Override
    public SpotCheckRuleModel copySourceToTarget(SpotCheckRuleEntity spotCheckRuleEntity, SpotCheckRuleModel spotCheckRuleModel) {
        spotCheckRuleModel.setSpotCheck(spotCheckConverter.sourceToTarget(spotCheckRuleEntity.getSpotCheck()));
        spotCheckRuleModel.setSpotCheckNumber(spotCheckRuleEntity.getSpotCheckNumber());
        spotCheckRuleModel.setRuleType(spotCheckRuleEntity.getRuleType());
        spotCheckRuleModel.setScoreItems(scoreItemsConverter.sourceToTarget(spotCheckRuleEntity.getScoreItems()));
        spotCheckRuleModel.setScoreItemType(spotCheckRuleEntity.getScoreItemType());
        spotCheckRuleModel.setId(spotCheckRuleEntity.getId());
        return spotCheckRuleModel;
    }

    @Override
    public SpotCheckRuleEntity copyTargetToSource(SpotCheckRuleModel spotCheckRuleModel, SpotCheckRuleEntity spotCheckRuleEntity) {
        spotCheckRuleEntity.setSpotCheck(spotCheckConverter.targetToSource(spotCheckRuleModel.getSpotCheck()));
        spotCheckRuleEntity.setSpotCheckNumber(spotCheckRuleModel.getSpotCheckNumber());
        spotCheckRuleEntity.setRuleType(spotCheckRuleModel.getRuleType());
        spotCheckRuleEntity.setScoreItems(scoreItemsConverter.targetToSource(spotCheckRuleModel.getScoreItems()));
        spotCheckRuleEntity.setScoreItemType(spotCheckRuleModel.getScoreItemType());
        spotCheckRuleEntity.setId(spotCheckRuleModel.getId());
        return spotCheckRuleEntity;
    }
}