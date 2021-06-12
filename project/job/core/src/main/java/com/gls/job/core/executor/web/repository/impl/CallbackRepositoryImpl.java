package com.gls.job.core.executor.web.repository.impl;

import com.gls.job.core.api.model.CallbackModel;
import com.gls.job.core.executor.constants.JobExecutorProperties;
import com.gls.job.core.executor.web.repository.CallbackRepository;
import com.xxl.job.core.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
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

    private final GenericJackson2JsonRedisSerializer jsonRedisSerializer = new GenericJackson2JsonRedisSerializer();

    @Resource
    private JobExecutorProperties jobExecutorProperties;
    private final String baseFileName = jobExecutorProperties.getCallbackLogPath().concat("/gls-job-callback-{x}.log");

    @Override
    public void save(List<CallbackModel> callbackModels) {
        String fileName = baseFileName.replace("{x}", String.valueOf(System.currentTimeMillis()));
        byte[] callbackModelsBytes = jsonRedisSerializer.serialize(callbackModels);
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
                List<CallbackModel> callbackModelList = jsonRedisSerializer.deserialize(callbackModelsBytes, List.class);
                callbackModels.addAll(callbackModelList);
                FileUtil.deleteFile(fileName);
            }
        }
        return callbackModels;
    }
}