package com.gls.job.admin.web.service;

import com.gls.job.admin.core.util.I18nUtil;
import com.gls.job.admin.web.dao.JobGroupDao;
import com.gls.job.admin.web.dao.JobInfoDao;
import com.gls.job.admin.web.dao.JobLogDao;
import com.gls.job.admin.web.entity.JobGroup;
import com.gls.job.admin.web.entity.JobInfo;
import com.gls.job.admin.web.entity.JobLog;
import com.gls.job.admin.web.entity.enums.ExecutorRouteStrategy;
import com.gls.job.admin.web.entity.enums.TriggerType;
import com.gls.job.core.api.model.Result;
import com.gls.job.core.api.model.TriggerModel;
import com.gls.job.core.api.model.enums.ExecutorBlockStrategy;
import com.gls.job.core.api.rpc.ExecutorApi;
import com.gls.job.core.util.IpUtil;
import com.gls.job.core.util.ThrowableUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * gls-job trigger
 *
 * @author george
 * @date 17/7/13
 */
@Slf4j
@Service
public class JobTriggerService {

    @Resource
    private JobLogDao jobLogDao;

    @Resource
    private JobInfoDao jobInfoDao;

    @Resource
    private JobGroupDao jobGroupDao;

    @Resource
    private JobSchedulerService jobSchedulerService;

    /**
     * trigger job
     *
     * @param jobId
     * @param triggerType
     * @param failRetryCount        >=0: use this param
     *                              <0: use param from job info config
     * @param executorShardingParam
     * @param executorParam         null: use job param
     *                              not null: cover job param
     * @param addressList           null: use executor addressList
     *                              not null: cover
     */
    public void trigger(Long jobId,
                        TriggerType triggerType,
                        int failRetryCount,
                        String executorShardingParam,
                        String executorParam,
                        String addressList) {

        // load data
        JobInfo jobInfo = jobInfoDao.loadById(jobId);
        if (jobInfo == null) {
            log.warn(">>>>>>>>>>>> trigger fail, jobId invalid，jobId={}", jobId);
            return;
        }
        if (executorParam != null) {
            jobInfo.setExecutorParam(executorParam);
        }
        int finalFailRetryCount = failRetryCount >= 0 ? failRetryCount : jobInfo.getExecutorFailRetryCount();
        JobGroup group = jobGroupDao.load(jobInfo.getJobGroup());

        // cover addressList
        if (addressList != null && addressList.trim().length() > 0) {
            group.setAddressType(1);
            group.setAddressList(addressList.trim());
        }

        // sharding param
        int[] shardingParam = null;
        if (executorShardingParam != null) {
            String[] shardingArr = executorShardingParam.split("/");
            if (shardingArr.length == 2 && isNumeric(shardingArr[0]) && isNumeric(shardingArr[1])) {
                shardingParam = new int[2];
                shardingParam[0] = Integer.parseInt(shardingArr[0]);
                shardingParam[1] = Integer.parseInt(shardingArr[1]);
            }
        }
        if (ExecutorRouteStrategy.SHARDING_BROADCAST == jobInfo.getExecutorRouteStrategy()
                && group.getRegistryList() != null && !group.getRegistryList().isEmpty()
                && shardingParam == null) {
            for (int i = 0; i < group.getRegistryList().size(); i++) {
                processTrigger(group, jobInfo, finalFailRetryCount, triggerType, i, group.getRegistryList().size());
            }
        } else {
            if (shardingParam == null) {
                shardingParam = new int[]{0, 1};
            }
            processTrigger(group, jobInfo, finalFailRetryCount, triggerType, shardingParam[0], shardingParam[1]);
        }

    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * @param group               job group, registry list may be empty
     * @param jobInfo
     * @param finalFailRetryCount
     * @param triggerType
     * @param index               sharding index
     * @param total               sharding index
     */
    private void processTrigger(JobGroup group, JobInfo jobInfo, int finalFailRetryCount, TriggerType triggerType, int index, int total) {

        // param
        ExecutorBlockStrategy blockStrategy = jobInfo.getExecutorBlockStrategy();  // block strategy
        ExecutorRouteStrategy executorRouteStrategyEnum = jobInfo.getExecutorRouteStrategy();    // route strategy
        String shardingParam = (ExecutorRouteStrategy.SHARDING_BROADCAST == executorRouteStrategyEnum) ? String.valueOf(index).concat("/").concat(String.valueOf(total)) : null;

        // 1、save log-id
        JobLog jobLog = new JobLog();
        jobLog.setJobGroup(jobInfo.getJobGroup());
        jobLog.setJobId(jobInfo.getId());
        jobLog.setTriggerTime(new Date());
        jobLogDao.save(jobLog);
        log.debug(">>>>>>>>>>> gls-job trigger start, jobId:{}", jobLog.getId());

        // 2、init trigger-param
        TriggerModel triggerModel = new TriggerModel();
        triggerModel.setJobId(jobInfo.getId());
        triggerModel.setExecutorHandler(jobInfo.getExecutorHandler());
        triggerModel.setExecutorParams(jobInfo.getExecutorParam());
        triggerModel.setExecutorBlockStrategy(jobInfo.getExecutorBlockStrategy());
        triggerModel.setExecutorTimeout(jobInfo.getExecutorTimeout());
        triggerModel.setLogId(jobLog.getId());
        triggerModel.setLogDateTime(jobLog.getTriggerTime());
        triggerModel.setGlueType(jobInfo.getGlueType());
        triggerModel.setGlueSource(jobInfo.getGlueSource());
        triggerModel.setGlueUpdateTime(jobInfo.getGlueUpdateTime());
        triggerModel.setBroadcastIndex(index);
        triggerModel.setBroadcastTotal(total);

        // 3、init address
        String address = null;
        Result<String> routeAddressResult = null;
        if (group.getRegistryList() != null && !group.getRegistryList().isEmpty()) {
            if (ExecutorRouteStrategy.SHARDING_BROADCAST == executorRouteStrategyEnum) {
                if (index < group.getRegistryList().size()) {
                    address = group.getRegistryList().get(index);
                } else {
                    address = group.getRegistryList().get(0);
                }
            } else {
                routeAddressResult = executorRouteStrategyEnum.getRouter().route(triggerModel, group.getRegistryList());
                if (routeAddressResult.getCode() == Result.SUCCESS_CODE) {
                    address = routeAddressResult.getContent();
                }
            }
        } else {
            routeAddressResult = new Result<>(Result.FAIL_CODE, I18nUtil.getString("job_conf_trigger_address_empty"));
        }

        // 4、trigger remote executor
        Result<String> triggerResult;
        if (address != null) {
            triggerResult = runExecutor(triggerModel, address);
        } else {
            triggerResult = new Result<>(Result.FAIL_CODE, null);
        }

        // 5、collection trigger info
        StringBuilder triggerMsgSb = new StringBuilder();
        triggerMsgSb.append(I18nUtil.getString("job_conf_trigger_type")).append("：").append(triggerType.getTitle());
        triggerMsgSb.append("<br>").append(I18nUtil.getString("job_conf_trigger_admin_address")).append("：").append(IpUtil.getIp());
        triggerMsgSb.append("<br>").append(I18nUtil.getString("job_conf_trigger_exe_reg_type")).append("：")
                .append((group.getAddressType() == 0) ? I18nUtil.getString("job_group_field_addressType_0") : I18nUtil.getString("job_group_field_addressType_1"));
        triggerMsgSb.append("<br>").append(I18nUtil.getString("job_conf_trigger_exe_reg_address")).append("：").append(group.getRegistryList());
        triggerMsgSb.append("<br>").append(I18nUtil.getString("job_info_field_executorRouteStrategy")).append("：").append(executorRouteStrategyEnum.getTitle());
        if (shardingParam != null) {
            triggerMsgSb.append("(").append(shardingParam).append(")");
        }
        triggerMsgSb.append("<br>").append(I18nUtil.getString("job_info_field_executorBlockStrategy")).append("：").append(blockStrategy.getTitle());
        triggerMsgSb.append("<br>").append(I18nUtil.getString("job_info_field_timeout")).append("：").append(jobInfo.getExecutorTimeout());
        triggerMsgSb.append("<br>").append(I18nUtil.getString("job_info_field_executorFailRetryCount")).append("：").append(finalFailRetryCount);

        triggerMsgSb.append("<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>").append(I18nUtil.getString("job_conf_trigger_run")).append("<<<<<<<<<<< </span><br>")
                .append((routeAddressResult != null && routeAddressResult.getMsg() != null) ? routeAddressResult.getMsg() + "<br><br>" : "").append(triggerResult.getMsg() != null ? triggerResult.getMsg() : "");

        // 6、save log trigger-info
        jobLog.setExecutorAddress(address);
        jobLog.setExecutorHandler(jobInfo.getExecutorHandler());
        jobLog.setExecutorParam(jobInfo.getExecutorParam());
        jobLog.setExecutorShardingParam(shardingParam);
        jobLog.setExecutorFailRetryCount(finalFailRetryCount);
        //jobLog.setTriggerTime();
        jobLog.setTriggerCode(triggerResult.getCode());
        jobLog.setTriggerMsg(triggerMsgSb.toString());
        jobLogDao.updateTriggerInfo(jobLog);

        log.debug(">>>>>>>>>>> gls-job trigger end, jobId:{}", jobLog.getId());
    }

    /**
     * run executor
     *
     * @param triggerModel
     * @param address
     * @return
     */
    public Result<String> runExecutor(TriggerModel triggerModel, String address) {
        Result<String> runResult;
        try {
            ExecutorApi executorApi = jobSchedulerService.getExecutorApi(address);
            runResult = executorApi.run(triggerModel);
        } catch (Exception e) {
            log.error(">>>>>>>>>>> gls-job trigger error, please check if the executor[{}] is running.", address, e);
            runResult = new Result<>(Result.FAIL_CODE, ThrowableUtil.toString(e));
        }

        String runResultStringBuffer = I18nUtil.getString("job_conf_trigger_run") + "：" + "<br>address：" + address +
                "<br>code：" + runResult.getCode() +
                "<br>msg：" + runResult.getMsg();
        runResult.setMsg(runResultStringBuffer);
        return runResult;
    }

}
