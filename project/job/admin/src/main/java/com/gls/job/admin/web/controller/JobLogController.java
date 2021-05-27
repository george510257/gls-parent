package com.gls.job.admin.web.controller;

import com.gls.job.admin.core.complete.JobCompleter;
import com.gls.job.admin.core.exception.JobException;
import com.gls.job.admin.core.scheduler.JobScheduler;
import com.gls.job.admin.core.util.I18nUtil;
import com.gls.job.admin.web.entity.JobGroupEntity;
import com.gls.job.admin.web.entity.JobInfoEntity;
import com.gls.job.admin.web.entity.JobLogEntity;
import com.gls.job.admin.web.repository.JobGroupRepository;
import com.gls.job.admin.web.repository.JobInfoRepository;
import com.gls.job.admin.web.repository.JobLogRepository;
import com.gls.job.core.biz.ExecutorBiz;
import com.gls.job.core.biz.model.KillParam;
import com.gls.job.core.biz.model.LogParam;
import com.gls.job.core.biz.model.LogResult;
import com.gls.job.core.biz.model.ReturnT;
import com.gls.job.core.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * index controller
 *
 * @author xuxueli 2015-12-19 16:13:16
 */
@Slf4j
@Controller
@RequestMapping("/joblog")
public class JobLogController {
    @Resource
    public JobInfoRepository jobInfoRepository;
    @Resource
    public JobLogRepository jobLogRepository;
    @Resource
    private JobGroupRepository jobGroupRepository;

    @RequestMapping
    public String index(HttpServletRequest request, Model model, @RequestParam(required = false, defaultValue = "0") Integer jobId) {

        // 执行器列表
        List<JobGroupEntity> jobGroupEntityListAll = jobGroupRepository.findAll();

        // filter group
        List<JobGroupEntity> jobGroupEntityList = JobInfoController.filterJobGroupByRole(request, jobGroupEntityListAll);
        if (jobGroupEntityList == null || jobGroupEntityList.size() == 0) {
            throw new JobException(I18nUtil.getString("job_group_empty"));
        }

        model.addAttribute("JobGroupList", jobGroupEntityList);

        // 任务
        if (jobId > 0) {
            JobInfoEntity jobInfoEntity = jobInfoRepository.loadById(jobId);
            if (jobInfoEntity == null) {
                throw new RuntimeException(I18nUtil.getString("job_info_field_id") + I18nUtil.getString("system_un_valid"));
            }

            model.addAttribute("jobInfo", jobInfoEntity);

            // valid permission
            JobInfoController.validPermission(request, jobInfoEntity.getJobGroup());
        }

        return "joblog/joblog.index";
    }

    @RequestMapping("/getJobsByGroup")
    @ResponseBody
    public ReturnT<List<JobInfoEntity>> getJobsByGroup(int jobGroup) {
        List<JobInfoEntity> list = jobInfoRepository.getJobsByGroup(jobGroup);
        return new ReturnT<>(list);
    }

    @RequestMapping("/pageList")
    @ResponseBody
    public Map<String, Object> pageList(HttpServletRequest request,
                                        @RequestParam(required = false, defaultValue = "0") int start,
                                        @RequestParam(required = false, defaultValue = "10") int length,
                                        int jobGroup, int jobId, int logStatus, String filterTime) {

        // valid permission
        JobInfoController.validPermission(request, jobGroup);
        // 仅管理员支持查询全部；普通用户仅支持查询有权限的 jobGroup

        // parse param
        Date triggerTimeStart = null;
        Date triggerTimeEnd = null;
        if (filterTime != null && filterTime.trim().length() > 0) {
            String[] temp = filterTime.split(" - ");
            if (temp.length == 2) {
                triggerTimeStart = DateUtil.parseDateTime(temp[0]);
                triggerTimeEnd = DateUtil.parseDateTime(temp[1]);
            }
        }

        // page query
        List<JobLogEntity> list = jobLogRepository.pageList(start, length, jobGroup, jobId, triggerTimeStart, triggerTimeEnd, logStatus);
        int listCount = jobLogRepository.pageListCount(start, length, jobGroup, jobId, triggerTimeStart, triggerTimeEnd, logStatus);

        // package result
        Map<String, Object> maps = new HashMap<>(3);
        maps.put("recordsTotal", listCount);
        // 总记录数
        maps.put("recordsFiltered", listCount);
        // 过滤后的总记录数
        maps.put("data", list);
        // 分页列表
        return maps;
    }

    @RequestMapping("/logDetailPage")
    public String logDetailPage(int id, Model model) {

        // base check
        JobLogEntity jobLogEntity = jobLogRepository.load(id);
        if (jobLogEntity == null) {
            throw new RuntimeException(I18nUtil.getString("job_log_log_id_un_valid"));
        }

        model.addAttribute("triggerCode", jobLogEntity.getTriggerCode());
        model.addAttribute("handleCode", jobLogEntity.getHandleCode());
        model.addAttribute("executorAddress", jobLogEntity.getExecutorAddress());
        model.addAttribute("triggerTime", jobLogEntity.getTriggerTime().getTime());
        model.addAttribute("logId", jobLogEntity.getId());
        return "joblog/joblog.detail";
    }

    @RequestMapping("/logDetailCat")
    @ResponseBody
    public ReturnT<LogResult> logDetailCat(String executorAddress, long triggerTime, long logId, int fromLineNum) {
        try {
            ExecutorBiz executorBiz = JobScheduler.getExecutorBiz(executorAddress);
            ReturnT<LogResult> logResult = executorBiz.log(new LogParam(triggerTime, logId, fromLineNum));

            // is end
            if (logResult.getContent() != null && logResult.getContent().getFromLineNum() > logResult.getContent().getToLineNum()) {
                JobLogEntity jobLogEntity = jobLogRepository.load(logId);
                if (jobLogEntity.getHandleCode() > 0) {
                    logResult.getContent().setEnd(true);
                }
            }

            return logResult;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ReturnT<>(ReturnT.FAIL_CODE, e.getMessage());
        }
    }

    @RequestMapping("/logKill")
    @ResponseBody
    public ReturnT<String> logKill(int id) {
        // base check
        JobLogEntity jobLogEntity = jobLogRepository.load(id);
        JobInfoEntity jobInfoEntity = jobInfoRepository.loadById(jobLogEntity.getJobId());
        if (jobInfoEntity == null) {
            return new ReturnT<>(500, I18nUtil.getString("job_info_glue_job_id_un_valid"));
        }
        if (ReturnT.SUCCESS_CODE != jobLogEntity.getTriggerCode()) {
            return new ReturnT<>(500, I18nUtil.getString("job_log_kill_log_limit"));
        }

        // request of kill
        ReturnT<String> runResult;
        try {
            ExecutorBiz executorBiz = JobScheduler.getExecutorBiz(jobLogEntity.getExecutorAddress());
            runResult = executorBiz.kill(new KillParam(jobInfoEntity.getId()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            runResult = new ReturnT<>(500, e.getMessage());
        }

        if (ReturnT.SUCCESS_CODE == runResult.getCode()) {
            jobLogEntity.setHandleCode(ReturnT.FAIL_CODE);
            jobLogEntity.setHandleMsg(I18nUtil.getString("job_log_kill_log_by_man") + ":" + (runResult.getMsg() != null ? runResult.getMsg() : ""));
            jobLogEntity.setHandleTime(new Date());
            JobCompleter.updateHandleInfoAndFinish(jobLogEntity);
            return new ReturnT<>(runResult.getMsg());
        } else {
            return new ReturnT<>(500, runResult.getMsg());
        }
    }

    @RequestMapping("/clearLog")
    @ResponseBody
    public ReturnT<String> clearLog(int jobGroup, int jobId, int type) {

        Date clearBeforeTime = null;
        int clearBeforeNum = 0;
        switch (type) {
            case 1:
                clearBeforeTime = DateUtil.addMonths(new Date(), -1);
                // 清理一个月之前日志数据
                break;
            case 2:
                clearBeforeTime = DateUtil.addMonths(new Date(), -3);
                // 清理三个月之前日志数据
                break;
            case 3:
                clearBeforeTime = DateUtil.addMonths(new Date(), -6);
                // 清理六个月之前日志数据
                break;
            case 4:
                clearBeforeTime = DateUtil.addYears(new Date(), -1);
                // 清理一年之前日志数据
                break;
            case 5:
                clearBeforeNum = 1000;
                // 清理一千条以前日志数据
                break;
            case 6:
                clearBeforeNum = 10000;
                // 清理一万条以前日志数据
                break;
            case 7:
                clearBeforeNum = 30000;
                // 清理三万条以前日志数据
                break;
            case 8:
                clearBeforeNum = 100000;
                // 清理十万条以前日志数据
                break;
            case 9:
                clearBeforeNum = 0;
                // 清理所有日志数据
                break;
            default:
                return new ReturnT<>(ReturnT.FAIL_CODE, I18nUtil.getString("job_log_clean_type_un_valid"));
        }

        List<Long> logIds;
        do {
            logIds = jobLogRepository.findClearLogIds(jobGroup, jobId, clearBeforeTime, clearBeforeNum, 1000);
            if (logIds != null && logIds.size() > 0) {
                jobLogRepository.clearLog(logIds);
            }
        } while (logIds != null && logIds.size() > 0);

        return ReturnT.SUCCESS;
    }

}
