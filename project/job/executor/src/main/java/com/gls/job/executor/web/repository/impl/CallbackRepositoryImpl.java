package com.gls.job.executor.web.repository.impl;

import cn.hutool.core.io.FileUtil;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gls.job.core.api.model.CallbackModel;
import com.gls.job.executor.core.constants.JobExecutorProperties;
import com.gls.job.executor.web.repository.CallbackRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
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
    @Resource
    private ObjectMapper objectMapper;

    @Override
    public List<CallbackModel> getAll() {
        List<CallbackModel> callbackModels = new ArrayList<>();
        List<String> fileNames = FileUtil.listFileNames(jobExecutorProperties.getCallbackLogPath());
        if (!ObjectUtils.isEmpty(fileNames)) {
            for (String fileName : fileNames) {
                log.info("fileName : {}", fileName);
                JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, CallbackModel.class);
                try {
                    List<CallbackModel> callbackModelList = objectMapper.readValue(FileUtil.file(fileName), javaType);
                    callbackModels.addAll(callbackModelList);
                    FileUtil.del(fileName);
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return callbackModels;
    }

    @Override
    public void save(List<CallbackModel> callbackModels) {
        String fileName = getBaseFileName().replace("{x}", String.valueOf(System.currentTimeMillis()));
        try {
            objectMapper.writeValue(FileUtil.file(fileName), callbackModels);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private String getBaseFileName() {
        return jobExecutorProperties.getCallbackLogPath().concat("/gls-job-callback-{x}.log");
    }
}
