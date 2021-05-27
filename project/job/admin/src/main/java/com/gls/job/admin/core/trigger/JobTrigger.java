package com.gls.job.admin.core.trigger;

import com.gls.job.admin.core.conf.JobAdminConfig;
import com.gls.job.admin.core.route.ExecutorRouteStrategyEnum;
import com.gls.job.admin.core.scheduler.JobScheduler;
import com.gls.job.admin.core.util.I18nUtil;
import com.gls.job.admin.web.entity.JobGroupEntity;
import com.gls.job.admin.web.entity.JobInfoEntity;
import com.gls.job.admin.web.entity.JobLogEntity;
import com.gls.job.core.biz.ExecutorBiz;
import com.gls.job.core.biz.model.ReturnT;
import com.gls.job.core.biz.model.TriggerParam;
import com.gls.job.core.enums.ExecutorBlockStrategyEnum;
import com.gls.job.core.util.IpUtil;
import com.gls.job.core.util.ThrowableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * gls-job trigger
 * Created by xuxueli on 17/7/13.
 */
public class JobTrigger {
    private static Logger logger = LoggerFactory.getLogger(JobTrigger.class);

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
    public static void trigger(int jobId,
                               TriggerTypeEnum triggerType,
                               int failRetryCount,
                               String executorShardingParam,
                               String executorParam,
                               String addressList) {

        // load data
        JobInfoEntity jobInfoEntity = JobAdminConfig.getAdminConfig().getJobInfoRepository().loadById(jobId);
        if (jobInfoEntity == null) {
            logger.warn(">>>>>>>>>>>> trigger fail, jobId invalid，jobId={}", jobId);
            return;
        }
        if (executorParam != null) {
            jobInfoEntity.setExecutorParam(executorParam);
        }
        int finalFailRetryCount = failRetryCount >= 0 ? failRetryCount : jobInfoEntity.getExecutorFailRetryCount();
        JobGroupEntity group = JobAdminConfig.getAdminConfig().getJobGroupRepository().load(jobInfoEntity.getJobGroup());

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
                shardingParam[0] = Integer.valueOf(shardingArr[0]);
                shardingParam[1] = Integer.valueOf(shardingArr[1]);
            }
        }
        if (ExecutorRouteStrategyEnum.SHARDING_BROADCAST == ExecutorRouteStrategyEnum.match(jobInfoEntity.getExecutorRouteStrategy(), null)
                && group.getRegistryList() != null && !group.getRegistryList().isEmpty()
                && shardingParam == null) {
            for (int i = 0; i < group.getRegistryList().size(); i++) {
                processTrigger(group, jobInfoEntity, finalFailRetryCount, triggerType, i, group.getRegistryList().size());
            }
        } else {
            if (shardingParam == null) {
                shardingParam = new int[]{0, 1};
            }
            processTrigger(group, jobInfoEntity, finalFailRetryCount, triggerType, shardingParam[0], shardingParam[1]);
        }

    }

    private static boolean isNumeric(String str) {
        try {
            int result = Integer.valueOf(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * @param group               job group, registry list may be empty
     * @param jobInfoEntity
     * @param finalFailRetryCount
     * @param triggerType
     * @param index               sharding index
     * @param total               sharding index
     */
    private static void processTrigger(JobGroupEntity group, JobInfoEntity jobInfoEntity, int finalFailRetryCount, TriggerTypeEnum triggerType, int index, int total) {

        // param
        ExecutorBlockStrategyEnum blockStrategy = ExecutorBlockStrategyEnum.match(jobInfoEntity.getExecutorBlockStrategy(), ExecutorBlockStrategyEnum.SERIAL_EXECUTION);  // block strategy
        ExecutorRouteStrategyEnum executorRouteStrategyEnum = ExecutorRouteStrategyEnum.match(jobInfoEntity.getExecutorRouteStrategy(), null);    // route strategy
        String shardingParam = (ExecutorRouteStrategyEnum.SHARDING_BROADCAST == executorRouteStrategyEnum) ? String.valueOf(index).concat("/").concat(String.valueOf(total)) : null;

        // 1、save log-id
        JobLogEntity jobLogEntity = new JobLogEntity();
        jobLogEntity.setJobGroup(jobInfoEntity.getJobGroup());
        jobLogEntity.setJobId(jobInfoEntity.getId());
        jobLogEntity.setTriggerTime(new Date());
        JobAdminConfig.getAdminConfig().getJobLogRepository().save(jobLogEntity);
        logger.debug(">>>>>>>>>>> gls-job trigger start, jobId:{}", jobLogEntity.getId());

        // 2、init trigger-param
        TriggerParam triggerParam = new TriggerParam();
        triggerParam.setJobId(jobInfoEntity.getId());
        triggerParam.setExecutorHandler(jobInfoEntity.getExecutorHandler());
        triggerParam.setExecutorParams(jobInfoEntity.getExecutorParam());
        triggerParam.setExecutorBlockStrategy(jobInfoEntity.getExecutorBlockStrategy());
        triggerParam.setExecutorTimeout(jobInfoEntity.getExecutorTimeout());
        triggerParam.setLogId(jobLogEntity.getId());
        triggerParam.setLogDateTime(jobLogEntity.getTriggerTime().getTime());
        triggerParam.setGlueType(jobInfoEntity.getGlueType());
        triggerParam.setGlueSource(jobInfoEntity.getGlueSource());
        triggerParam.setGlueUpdatetime(jobInfoEntity.getGlueUpdatetime().getTime());
        triggerParam.setBroadcastIndex(index);
        triggerParam.setBroadcastTotal(total);

        // 3、init address
        String address = null;
        ReturnT<String> routeAddressResult = null;
        if (group.getRegistryList() != null && !group.getRegistryList().isEmpty()) {
            if (ExecutorRouteStrategyEnum.SHARDING_BROADCAST == executorRouteStrategyEnum) {
                if (index < group.getRegistryList().size()) {
                    address = group.getRegistryList().get(index);
                } else {
                    address = group.getRegistryList().get(0);
                }
            } else {
                routeAddressResult = executorRouteStrategyEnum.getRouter().route(triggerParam, group.getRegistryList());
                if (routeAddressResult.getCode() == ReturnT.SUCCESS_CODE) {
                    address = routeAddressResult.getContent();
                }
            }
        } else {
            routeAddressResult = new ReturnT<String>(ReturnT.FAIL_CODE, I18nUtil.getString("jobconf_trigger_address_empty"));
        }

        // 4、trigger remote executor
        ReturnT<String> triggerResult = null;
        if (address != null) {
            triggerResult = runExecutor(triggerParam, address);
        } else {
            triggerResult = new ReturnT<String>(ReturnT.FAIL_CODE, null);
        }

        // 5、collection trigger info
        StringBuffer triggerMsgSb = new StringBuffer();
        triggerMsgSb.append(I18nUtil.getString("jobconf_trigger_type")).append("：").append(triggerType.getTitle());
        triggerMsgSb.append("<br>").append(I18nUtil.getString("jobconf_trigger_admin_adress")).append("：").append(IpUtil.getIp());
        triggerMsgSb.append("<br>").append(I18nUtil.getString("jobconf_trigger_exe_regtype")).append("：")
                .append((group.getAddressType() == 0) ? I18nUtil.getString("job_group_field_addressType_0") : I18nUtil.getString("job_group_field_addressType_1"));
        triggerMsgSb.append("<br>").append(I18nUtil.getString("jobconf_trigger_exe_regaddress")).append("：").append(group.getRegistryList());
        triggerMsgSb.append("<br>").append(I18nUtil.getString("job_info_field_executorRouteStrategy")).append("：").append(executorRouteStrategyEnum.getTitle());
        if (shardingParam != null) {
            triggerMsgSb.append("(" + shardingParam + ")");
        }
        triggerMsgSb.append("<br>").append(I18nUtil.getString("job_info_field_executorBlockStrategy")).append("：").append(blockStrategy.getTitle());
        triggerMsgSb.append("<br>").append(I18nUtil.getString("job_info_field_timeout")).append("：").append(jobInfoEntity.getExecutorTimeout());
        triggerMsgSb.append("<br>").append(I18nUtil.getString("job_info_field_executorFailRetryCount")).append("：").append(finalFailRetryCount);

        triggerMsgSb.append("<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>" + I18nUtil.getString("jobconf_trigger_run") + "<<<<<<<<<<< </span><br>")
                .append((routeAddressResult != null && routeAddressResult.getMsg() != null) ? routeAddressResult.getMsg() + "<br><br>" : "").append(triggerResult.getMsg() != null ? triggerResult.getMsg() : "");

        // 6、save log trigger-info
        jobLogEntity.setExecutorAddress(address);
        jobLogEntity.setExecutorHandler(jobInfoEntity.getExecutorHandler());
        jobLogEntity.setExecutorParam(jobInfoEntity.getExecutorParam());
        jobLogEntity.setExecutorShardingParam(shardingParam);
        jobLogEntity.setExecutorFailRetryCount(finalFailRetryCount);
        //jobLogEntity.setTriggerTime();
        jobLogEntity.setTriggerCode(triggerResult.getCode());
        jobLogEntity.setTriggerMsg(triggerMsgSb.toString());
        JobAdminConfig.getAdminConfig().getJobLogRepository().updateTriggerInfo(jobLogEntity);

        logger.debug(">>>>>>>>>>> gls-job trigger end, jobId:{}", jobLogEntity.getId());
    }

    /**
     * run executor
     *
     * @param triggerParam
     * @param address
     * @return
     */
    public static ReturnT<String> runExecutor(TriggerParam triggerParam, String address) {
        ReturnT<String> runResult = null;
        try {
            ExecutorBiz executorBiz = JobScheduler.getExecutorBiz(address);
            runResult = executorBiz.run(triggerParam);
        } catch (Exception e) {
            logger.error(">>>>>>>>>>> gls-job trigger error, please check if the executor[{}] is running.", address, e);
            runResult = new ReturnT<String>(ReturnT.FAIL_CODE, ThrowableUtil.toString(e));
        }

        StringBuffer runResultSB = new StringBuffer(I18nUtil.getString("jobconf_trigger_run") + "：");
        runResultSB.append("<br>address：").append(address);
        runResultSB.append("<br>code：").append(runResult.getCode());
        runResultSB.append("<br>msg：").append(runResult.getMsg());

        runResult.setMsg(runResultSB.toString());
        return runResult;
    }

}
