package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.ResultTextSplitWordCustomerEntity;
import com.gls.quality.inspection.process.web.model.ResultTextSplitWordCustomerModel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class ResultTextSplitWordCustomerConverter implements BaseConverter<ResultTextSplitWordCustomerEntity, ResultTextSplitWordCustomerModel> {
    @Resource
    private ExtractCheckAudioConverter extractCheckAudioConverter;

    @Override
    public ResultTextSplitWordCustomerModel copySourceToTarget(ResultTextSplitWordCustomerEntity resultTextSplitWordCustomerEntity, ResultTextSplitWordCustomerModel resultTextSplitWordCustomerModel) {
        resultTextSplitWordCustomerModel.setNumber(resultTextSplitWordCustomerEntity.getNumber());
        resultTextSplitWordCustomerModel.setExtractCheckAudio(extractCheckAudioConverter.sourceToTarget(resultTextSplitWordCustomerEntity.getExtractCheckAudio()));
        resultTextSplitWordCustomerModel.setWord(resultTextSplitWordCustomerEntity.getWord());
        resultTextSplitWordCustomerModel.setId(resultTextSplitWordCustomerEntity.getId());
        return resultTextSplitWordCustomerModel;
    }

    @Override
    public ResultTextSplitWordCustomerEntity copyTargetToSource(ResultTextSplitWordCustomerModel resultTextSplitWordCustomerModel, ResultTextSplitWordCustomerEntity resultTextSplitWordCustomerEntity) {
        resultTextSplitWordCustomerEntity.setNumber(resultTextSplitWordCustomerModel.getNumber());
        resultTextSplitWordCustomerEntity.setExtractCheckAudio(extractCheckAudioConverter.targetToSource(resultTextSplitWordCustomerModel.getExtractCheckAudio()));
        resultTextSplitWordCustomerEntity.setWord(resultTextSplitWordCustomerModel.getWord());
        resultTextSplitWordCustomerEntity.setId(resultTextSplitWordCustomerModel.getId());
        return resultTextSplitWordCustomerEntity;
    }
}