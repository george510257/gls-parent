package com.gls.job.executor.web.repository.impl;

import com.gls.framework.core.utils.FileUtils;
import com.gls.framework.core.utils.JacksonUtil;
import com.gls.job.core.api.model.CallbackModel;
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
        byte[] callbackModelsBytes = JacksonUtil.writeValueAsBytes(callbackModels);
        try {
            IOUtils.write(callbackModelsBytes, new FileOutputStream(fileName));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public List<CallbackModel> getAll() {
        List<CallbackModel> callbackModels = new ArrayList<>();
        List<String> fileNames = FileUtils.findFiles(jobExecutorProperties.getCallbackLogPath());
        if (!ObjectUtils.isEmpty(fileNames)) {
            for (String fileName : fileNames) {
                byte[] callbackModelsBytes = FileUtils.readFile(fileName);
                List<CallbackModel> callbackModelList = JacksonUtil.readValue(callbackModelsBytes, List.class, CallbackModel.class);
                callbackModels.addAll(callbackModelList);
                FileUtils.deleteFile(fileName);
            }
        }
        return callbackModels;
    }

    private String getBaseFileName() {
        return jobExecutorProperties.getCallbackLogPath().concat("/gls-job-callback-{x}.log");
    }
}
