package com.gls.job.admin.web.service;

import com.gls.job.admin.core.enums.ExecutorRouteStrategy;
import com.gls.job.admin.core.enums.TriggerType;
import com.gls.job.admin.core.i18n.I18nHelper;
import com.gls.job.admin.core.route.ExecutorRouterHolder;
import com.gls.job.admin.web.dao.XxlJobGroupDao;
import com.gls.job.admin.web.dao.XxlJobInfoDao;
import com.gls.job.admin.web.dao.XxlJobLogDao;
import com.gls.job.admin.web.model.XxlJobGroup;
import com.gls.job.admin.web.model.XxlJobInfo;
import com.gls.job.admin.web.model.XxlJobLog;
import com.gls.job.core.api.model.Result;
import com.gls.job.core.api.model.TriggerModel;
import com.gls.job.core.api.model.enums.ExecutorBlockStrategy;
import com.gls.job.core.api.rpc.ExecutorApi;
import com.gls.job.core.util.IpUtil;
import com.gls.job.core.util.ThrowableUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * xxl-job trigger
 *
 * @author xuxueli
 * @date 17/7/13
 */
@Slf4j
@Component
public class XxlJobTrigger {
    @Resource
    public I18nHelper i18nHelper;
    @Resource
    private XxlJobScheduler xxlJobScheduler;
    @Resource
    private ExecutorRouterHolder executorRouterHolder;
    @Resource
    private XxlJobLogDao xxlJobLogDao;
    @Resource
    private XxlJobInfoDao xxlJobInfoDao;
    @Resource
    private XxlJobGroupDao xxlJobGroupDao;

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
        XxlJobInfo jobInfo = xxlJobInfoDao.loadById(jobId);
        if (jobInfo == null) {
            log.warn(">>>>>>>>>>>> trigger fail, jobId invalid，jobId={}", jobId);
            return;
        }
        if (executorParam != null) {
            jobInfo.setExecutorParam(executorParam);
        }
        int finalFailRetryCount = failRetryCount >= 0 ? failRetryCount : jobInfo.getExecutorFailRetryCount();
        XxlJobGroup group = xxlJobGroupDao.load(jobInfo.getJobGroup());

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
            int result = Integer.parseInt(str);
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
    private void processTrigger(XxlJobGroup group, XxlJobInfo jobInfo, int finalFailRetryCount, TriggerType triggerType, int index, int total) {

        // param
        ExecutorBlockStrategy blockStrategy = jobInfo.getExecutorBlockStrategy();
        // block strategy
        ExecutorRouteStrategy executorRouteStrategy = jobInfo.getExecutorRouteStrategy();
        // route strategy
        String shardingParam = (ExecutorRouteStrategy.SHARDING_BROADCAST == executorRouteStrategy) ? String.valueOf(index).concat("/").concat(String.valueOf(total)) : null;

        // 1、save log-id
        XxlJobLog jobLog = new XxlJobLog();
        jobLog.setJobGroup(jobInfo.getJobGroup());
        jobLog.setJobId(jobInfo.getId());
        jobLog.setTriggerTime(new Date());
        xxlJobLogDao.save(jobLog);
        log.debug(">>>>>>>>>>> xxl-job trigger start, jobId:{}", jobLog.getId());

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
            if (ExecutorRouteStrategy.SHARDING_BROADCAST == executorRouteStrategy) {
                if (index < group.getRegistryList().size()) {
                    address = group.getRegistryList().get(index);
                } else {
                    address = group.getRegistryList().get(0);
                }
            } else {
                address = executorRouterHolder.load(executorRouteStrategy.getRouterKey()).route(triggerModel, group.getRegistryList());
            }
        } else {
            routeAddressResult = new Result<>(Result.FAIL_CODE, i18nHelper.getString("job_conf_trigger_address_empty"));
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
        triggerMsgSb.append(i18nHelper.getString("job_conf_trigger_type")).append("：").append(triggerType.getTitle());
        triggerMsgSb.append("<br>").append(i18nHelper.getString("job_conf_trigger_admin_adress")).append("：").append(IpUtil.getIp());
        triggerMsgSb.append("<br>").append(i18nHelper.getString("job_conf_trigger_exe_regtype")).append("：")
                .append((group.getAddressType() == 0) ? i18nHelper.getString("jobgroup_field_addressType_0") : i18nHelper.getString("jobgroup_field_addressType_1"));
        triggerMsgSb.append("<br>").append(i18nHelper.getString("job_conf_trigger_exe_regaddress")).append("：").append(group.getRegistryList());
        triggerMsgSb.append("<br>").append(i18nHelper.getString("job_info_field_executorRouteStrategy")).append("：").append(executorRouteStrategy.getTitle());
        if (shardingParam != null) {
            triggerMsgSb.append("(").append(shardingParam).append(")");
        }
        triggerMsgSb.append("<br>").append(i18nHelper.getString("job_info_field_executorBlockStrategy")).append("：").append(blockStrategy.getTitle());
        triggerMsgSb.append("<br>").append(i18nHelper.getString("job_info_field_timeout")).append("：").append(jobInfo.getExecutorTimeout());
        triggerMsgSb.append("<br>").append(i18nHelper.getString("job_info_field_executorFailRetryCount")).append("：").append(finalFailRetryCount);

        triggerMsgSb.append("<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>").append(i18nHelper.getString("job_conf_trigger_run")).append("<<<<<<<<<<< </span><br>")
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
        xxlJobLogDao.updateTriggerInfo(jobLog);

        log.debug(">>>>>>>>>>> xxl-job trigger end, jobId:{}", jobLog.getId());
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
            ExecutorApi executorApi = xxlJobScheduler.getExecutorBiz(address);
            runResult = executorApi.run(triggerModel);
        } catch (Exception e) {
            log.error(">>>>>>>>>>> xxl-job trigger error, please check if the executor[{}] is running.", address, e);
            runResult = new Result<>(Result.FAIL_CODE, ThrowableUtil.toString(e));
        }

        String runResultStr = i18nHelper.getString("job_conf_trigger_run") + "：" + "<br>address：" + address +
                "<br>code：" + runResult.getCode() +
                "<br>msg：" + runResult.getMsg();
        runResult.setMsg(runResultStr);
        return runResult;
    }

}
