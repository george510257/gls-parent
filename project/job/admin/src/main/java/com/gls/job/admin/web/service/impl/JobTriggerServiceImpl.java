package com.gls.job.admin.web.service.impl;

import cn.hutool.core.net.NetUtil;
import com.gls.framework.api.result.Result;
import com.gls.framework.core.util.StringUtil;
import com.gls.job.admin.core.route.ExecutorRouterHolder;
import com.gls.job.admin.web.entity.JobGroupEntity;
import com.gls.job.admin.web.entity.JobInfoEntity;
import com.gls.job.admin.web.entity.JobLogEntity;
import com.gls.job.admin.web.entity.JobLogGlueEntity;
import com.gls.job.admin.web.repository.JobInfoRepository;
import com.gls.job.admin.web.repository.JobLogGlueRepository;
import com.gls.job.admin.web.repository.JobLogRepository;
import com.gls.job.admin.web.service.JobTriggerService;
import com.gls.job.core.api.model.TriggerModel;
import com.gls.job.core.api.rpc.holder.ExecutorApiHolder;
import com.gls.job.core.constants.ExecutorRouteStrategy;
import com.gls.job.core.constants.TriggerType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author george
 */
@Slf4j
@Service
public class JobTriggerServiceImpl implements JobTriggerService {
    @Resource
    private JobInfoRepository jobInfoRepository;
    @Resource
    private JobLogRepository jobLogRepository;
    @Resource
    private JobLogGlueRepository jobLogGlueRepository;
    @Resource
    private ExecutorRouterHolder executorRouterHolder;
    @Resource
    private ExecutorApiHolder executorApiHolder;

    @Override
    public void trigger(Long jobId, TriggerType triggerType, int failRetryCount, String executorShardingParam, String executorParam, List<String> addressList) {
        // load data
        JobInfoEntity jobInfoEntity = jobInfoRepository.getOne(jobId);
        if (ObjectUtils.isEmpty(jobInfoEntity)) {
            log.warn(">>>>>>>>>>>> trigger fail, jobId invalid，jobId={}", jobId);
            return;
        }
        if (StringUtils.hasText(executorParam)) {
            jobInfoEntity.setExecutorParam(executorParam);
        }
        int finalFailRetryCount = failRetryCount >= 0 ? failRetryCount : jobInfoEntity.getExecutorFailRetryCount();
        JobGroupEntity jobGroupEntity = jobInfoEntity.getJobGroup();
        // cover addressList
        if (!ObjectUtils.isEmpty(addressList)) {
            jobGroupEntity.setAddressType(false);
            jobGroupEntity.setAddressList(addressList);
        }
        // sharding param
        int[] shardingParam = null;
        if (StringUtils.hasText(executorShardingParam)) {
            shardingParam = Arrays.stream(executorShardingParam.split("/")).mapToInt(Integer::parseInt).toArray();
        }
        if (ExecutorRouteStrategy.SHARDING_BROADCAST.equals(jobInfoEntity.getExecutorRouteStrategy())
                && !ObjectUtils.isEmpty(jobGroupEntity.getAddressList())
                && ObjectUtils.isEmpty(shardingParam)) {
            for (int i = 0; i < jobGroupEntity.getAddressList().size(); i++) {
                processTrigger(jobInfoEntity, finalFailRetryCount, triggerType, i, jobGroupEntity.getAddressList().size());
            }
        } else {
            if (shardingParam == null) {
                shardingParam = new int[]{0, 1};
            }
            processTrigger(jobInfoEntity, finalFailRetryCount, triggerType, shardingParam[0], shardingParam[1]);
        }
    }

    private void processTrigger(JobInfoEntity jobInfoEntity, int finalFailRetryCount, TriggerType triggerType, int index, int total) {
        // param
        String shardingParam = ExecutorRouteStrategy.SHARDING_BROADCAST.equals(jobInfoEntity.getExecutorRouteStrategy()) ? String.valueOf(index).concat("/").concat(String.valueOf(total)) : null;
        // 1、save log-id
        JobLogEntity jobLogEntity = createJobLogEntity(jobInfoEntity);
        // 2、init trigger-param
        TriggerModel triggerModel = createTriggerModel(jobLogEntity, index, total);
        // 3、init address
        String address = getAddress(jobInfoEntity, index);
        // 4、trigger remote executor
        Result<String> result = runExecutor(triggerModel, address);
        // 5、collection trigger info
        String triggerMsg = getTriggerMsg(jobInfoEntity, triggerType, shardingParam, finalFailRetryCount, result);
        // 6、save log trigger-info
        jobLogEntity.setExecutorAddress(address);
        jobLogEntity.setExecutorHandler(jobInfoEntity.getExecutorHandler());
        jobLogEntity.setExecutorParam(jobInfoEntity.getExecutorParam());
        jobLogEntity.setExecutorShardingParam(shardingParam);
        jobLogEntity.setExecutorFailRetryCount(finalFailRetryCount);
        jobLogEntity.setTriggerCode(result.getCode());
        jobLogEntity.setTriggerMsg(triggerMsg);
        jobLogRepository.save(jobLogEntity);
    }

    private JobLogEntity createJobLogEntity(JobInfoEntity jobInfoEntity) {
        JobLogEntity jobLogEntity = new JobLogEntity();
        jobLogEntity.setJobInfo(jobInfoEntity);
        jobLogEntity.setTriggerTime(new Date());
        jobLogEntity = jobLogRepository.save(jobLogEntity);
        log.debug(">>>>>>>>>>> gls-job trigger start, jobId:{}", jobLogEntity.getId());
        return jobLogEntity;
    }

    private TriggerModel createTriggerModel(JobLogEntity jobLogEntity, int index, int total) {
        JobInfoEntity jobInfoEntity = jobLogEntity.getJobInfo();
        JobLogGlueEntity jobLogGlueEntity = jobLogGlueRepository.getTopByJobInfoIdOrderByUpdateDateDesc(jobInfoEntity.getId());
        TriggerModel triggerModel = new TriggerModel();
        triggerModel.setJobId(jobInfoEntity.getId());
        triggerModel.setExecutorHandler(jobInfoEntity.getExecutorHandler());
        triggerModel.setExecutorParams(jobInfoEntity.getExecutorParam());
        triggerModel.setExecutorBlockStrategy(jobInfoEntity.getExecutorBlockStrategy());
        triggerModel.setExecutorTimeout(jobInfoEntity.getExecutorTimeout());
        triggerModel.setLogId(jobLogEntity.getId());
        triggerModel.setLogDateTime(jobLogEntity.getTriggerTime());
        triggerModel.setGlueType(jobLogGlueEntity.getGlueType());
        triggerModel.setGlueSource(jobLogGlueEntity.getGlueSource());
        triggerModel.setGlueUpdateTime(jobLogGlueEntity.getUpdateDate());
        triggerModel.setBroadcastIndex(index);
        triggerModel.setBroadcastTotal(total);
        return triggerModel;
    }

    private String getAddress(JobInfoEntity jobInfoEntity, int index) {
        if (ExecutorRouteStrategy.SHARDING_BROADCAST.equals(jobInfoEntity.getExecutorRouteStrategy())) {
            if (index < jobInfoEntity.getJobGroup().getAddressList().size()) {
                return jobInfoEntity.getJobGroup().getAddressList().get(index);
            } else {
                return jobInfoEntity.getJobGroup().getAddressList().get(0);
            }
        }
        return executorRouterHolder.load(jobInfoEntity.getExecutorRouteStrategy().getRouterKey())
                .route(jobInfoEntity.getId(), jobInfoEntity.getJobGroup().getAddressList());
    }

    private Result<String> runExecutor(TriggerModel triggerModel, String address) {
        Result<String> result;
        try {
            result = executorApiHolder.load(address).run(triggerModel);
        } catch (Exception e) {
            log.error(">>>>>>>>>>> gls-job trigger error, please check if the executor[{}] is running.", address, e);
            result = new Result<>(Result.FAIL_CODE, Arrays.toString(e.getStackTrace()));
        }
        result.setMessage("触发调度: <br>address：".concat(address)
                .concat("<br>code：").concat(String.valueOf(result.getCode()))
                .concat("<br>msg：").concat(result.getMessage()));
        return result;
    }

    private String getTriggerMsg(JobInfoEntity jobInfoEntity, TriggerType triggerType, String shardingParam, int finalFailRetryCount, Result<String> result) {
        StringBuilder triggerMsgBuilder = new StringBuilder();
        triggerMsgBuilder.append("任务触发类型：").append(triggerType.getTitle());
        triggerMsgBuilder.append("<br>调度机器：").append(NetUtil.getLocalhostStr());
        triggerMsgBuilder.append("<br>执行器-注册方式：").append(jobInfoEntity.getJobGroup().getAddressType() ? "自动注册" : "手动录入");
        triggerMsgBuilder.append("<br>执行器-地址列表：").append(StringUtil.toString(jobInfoEntity.getJobGroup().getAddressList()));
        triggerMsgBuilder.append("<br>路由策略：").append(jobInfoEntity.getExecutorRouteStrategy().getTitle());
        if (shardingParam != null) {
            triggerMsgBuilder.append("(").append(shardingParam).append(")");
        }
        triggerMsgBuilder.append("<br>阻塞处理策略：").append(jobInfoEntity.getExecutorBlockStrategy().getTitle());
        triggerMsgBuilder.append("<br>任务超时时间：").append(jobInfoEntity.getExecutorTimeout());
        triggerMsgBuilder.append("<br>失败重试次数：").append(finalFailRetryCount);
        triggerMsgBuilder.append("<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>").append(result);
        return triggerMsgBuilder.toString();
    }
}
