package com.gls.quality.inspection.process.web.converter;

import com.gls.framework.core.base.BaseConverter;
import com.gls.quality.inspection.process.web.entity.ResultTextSplitWordEntity;
import com.gls.quality.inspection.process.web.model.ResultTextSplitWordModel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author george
 */
@Component
public class ResultTextSplitWordConverter implements BaseConverter<ResultTextSplitWordEntity, ResultTextSplitWordModel> {
    @Resource
    private ExtractCheckAudioConverter extractCheckAudioConverter;

    @Override
    public ResultTextSplitWordModel copySourceToTarget(ResultTextSplitWordEntity resultTextSplitWordEntity, ResultTextSplitWordModel resultTextSplitWordModel) {
        resultTextSplitWordModel.setNumber(resultTextSplitWordEntity.getNumber());
        resultTextSplitWordModel.setExtractCheckAudio(extractCheckAudioConverter.sourceToTarget(resultTextSplitWordEntity.getExtractCheckAudio()));
        resultTextSplitWordModel.setWord(resultTextSplitWordEntity.getWord());
        resultTextSplitWordModel.setId(resultTextSplitWordEntity.getId());
        return resultTextSplitWordModel;
    }

    @Override
    public ResultTextSplitWordEntity copyTargetToSource(ResultTextSplitWordModel resultTextSplitWordModel, ResultTextSplitWordEntity resultTextSplitWordEntity) {
        resultTextSplitWordEntity.setNumber(resultTextSplitWordModel.getNumber());
        resultTextSplitWordEntity.setExtractCheckAudio(extractCheckAudioConverter.targetToSource(resultTextSplitWordModel.getExtractCheckAudio()));
        resultTextSplitWordEntity.setWord(resultTextSplitWordModel.getWord());
        resultTextSplitWordEntity.setId(resultTextSplitWordModel.getId());
        return resultTextSplitWordEntity;
    }
}