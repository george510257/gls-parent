package com.gls.job.admin.web.service.impl;

import cn.hutool.core.date.DateUtil;
import com.gls.framework.api.result.Result;
import com.gls.framework.core.exception.GlsException;
import com.gls.job.admin.web.entity.JobInfoEntity;
import com.gls.job.admin.web.entity.JobLogEntity;
import com.gls.job.admin.web.repository.JobLogRepository;
import com.gls.job.admin.web.service.AsyncService;
import com.gls.job.admin.web.service.JobCompleteService;
import com.gls.job.core.api.model.CallbackModel;
import com.gls.job.core.constants.JobConstants;
import com.gls.job.core.constants.TriggerType;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author george
 */
@Service
public class JobCompleteServiceImpl implements JobCompleteService {
    private static final int HANDLE_MSG_MAX_LENGTH = 15000;
    @Resource
    private JobLogRepository jobLogRepository;
    @Resource
    private AsyncService asyncService;

    @Override
    public void run() {
        Date lostTime = DateUtil.offsetMinute(new Date(), -10);
        List<JobLogEntity> jobLogEntities = jobLogRepository.getLostJobLogs(lostTime);
        if (ObjectUtils.isEmpty(jobLogEntities)) {
            return;
        }
        jobLogEntities.forEach(jobLogEntity -> {
            jobLogEntity.setHandleCode(Result.FAIL_CODE);
            jobLogEntity.setHandleMsg("任务结果丢失，标记失败");
            jobLogEntity.setHandleTime(new Date());
            updateHandleInfoAndFinish(jobLogEntity);
        });
    }

    @Override
    public void callback(List<CallbackModel> callbackModels) {
        if (ObjectUtils.isEmpty(callbackModels)) {
            return;
        }
        callbackModels.forEach(callbackModel -> {
            JobLogEntity jobLogEntity = jobLogRepository.getOne(callbackModel.getLogId());
            if (ObjectUtils.isEmpty(jobLogEntity)) {
                throw new GlsException("log item not found.");
            }
            if (jobLogEntity.getHandleCode() > 0) {
                throw new GlsException("log repeat callback.");
            }
            StringBuilder handleMsgBuilder = new StringBuilder();
            if (StringUtils.hasText(jobLogEntity.getHandleMsg())) {
                handleMsgBuilder.append(jobLogEntity.getHandleMsg()).append("<br>");
            }
            if (StringUtils.hasText(callbackModel.getHandleMsg())) {
                handleMsgBuilder.append(callbackModel.getHandleMsg());
            }
            jobLogEntity.setHandleCode(callbackModel.getHandleCode());
            jobLogEntity.setHandleMsg(handleMsgBuilder.toString());
            jobLogEntity.setHandleTime(new Date());
            updateHandleInfoAndFinish(jobLogEntity);
        });
    }

    private void updateHandleInfoAndFinish(JobLogEntity jobLogEntity) {
        finishJob(jobLogEntity);
        if (jobLogEntity.getHandleMsg().length() > HANDLE_MSG_MAX_LENGTH) {
            jobLogEntity.setHandleMsg(jobLogEntity.getHandleMsg().substring(0, HANDLE_MSG_MAX_LENGTH));
        }
        jobLogRepository.save(jobLogEntity);
    }

    private void finishJob(JobLogEntity jobLogEntity) {
        // JobInfoEntity
        StringBuilder triggerChildMsgBuilder = new StringBuilder();
        if (JobConstants.HANDLE_CODE_SUCCESS == jobLogEntity.getHandleCode()) {
            JobInfoEntity jobInfoEntity = jobLogEntity.getJobInfo();
            if (!ObjectUtils.isEmpty(jobInfoEntity)) {
                List<JobInfoEntity> childJobInfoEntities = jobInfoEntity.getChildJobs();
                if (!ObjectUtils.isEmpty(childJobInfoEntities)) {
                    for (int i = 0; i < childJobInfoEntities.size(); i++) {
                        JobInfoEntity childJobInfoEntity = childJobInfoEntities.get(i);
                        asyncService.asyncTrigger(jobLogEntity.getJobInfo().getId(), TriggerType.RETRY, jobLogEntity.getExecutorFailRetryCount() - 1, jobLogEntity.getExecutorShardingParam(), jobLogEntity.getExecutorParam(), null);
                        triggerChildMsgBuilder.append(i).append("/").append(childJobInfoEntities.size())
                                .append(" [任务ID=").append(childJobInfoEntity.getId()).append("], 触发成功. <br>");
                    }
                }
            }
        }
        jobLogEntity.setHandleMsg(jobLogEntity.getHandleMsg().concat(triggerChildMsgBuilder.toString()));
    }
}
