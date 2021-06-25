package com.gls.job.executor.web.repository.impl;

import com.alibaba.fastjson.JSON;
import com.gls.job.core.api.model.CallbackModel;
import com.gls.job.core.util.FileUtil;
import com.gls.job.executor.core.constants.JobExecutorProperties;
import com.gls.job.executor.web.repository.CallbackRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author george
 */
@Slf4j
@Repository
public class CallbackRepositoryImpl implements CallbackRepository {

    @Resource
    private JobExecutorProperties jobExecutorProperties;

    @Override
    public void save(List<CallbackModel> callbackModels) {
        String fileName = getBaseFileName().replace("{x}", String.valueOf(System.currentTimeMillis()));
        byte[] callbackModelsBytes = JSON.toJSONBytes(callbackModels);
        try {
            IOUtils.write(callbackModelsBytes, new FileOutputStream(fileName));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public List<CallbackModel> getAll() {
        List<CallbackModel> callbackModels = new ArrayList<>();
        List<String> fileNames = FileUtil.findFiles(jobExecutorProperties.getCallbackLogPath());
        if (!ObjectUtils.isEmpty(fileNames)) {
            for (String fileName : fileNames) {
                byte[] callbackModelsBytes = FileUtil.readFile(fileName);
                List<CallbackModel> callbackModelList = JSON.parseObject(callbackModelsBytes, List.class);
                callbackModels.addAll(callbackModelList);
                FileUtil.deleteFile(fileName);
            }
        }
        return callbackModels;
    }

    private String getBaseFileName() {
        return jobExecutorProperties.getCallbackLogPath().concat("/gls-job-callback-{x}.log");
    }
}
