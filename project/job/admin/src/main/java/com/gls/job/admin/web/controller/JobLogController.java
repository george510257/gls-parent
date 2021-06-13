package com.gls.job.admin.web.controller;

import com.gls.job.admin.core.i18n.I18nHelper;
import com.gls.job.admin.web.dao.JobGroupDao;
import com.gls.job.admin.web.dao.JobInfoDao;
import com.gls.job.admin.web.dao.JobLogDao;
import com.gls.job.admin.web.model.JobGroup;
import com.gls.job.admin.web.model.JobInfo;
import com.gls.job.admin.web.model.JobLog;
import com.gls.job.admin.web.service.JobCompleter;
import com.gls.job.admin.web.service.JobScheduler;
import com.gls.job.core.api.model.KillModel;
import com.gls.job.core.api.model.LogModel;
import com.gls.job.core.api.model.LogResultModel;
import com.gls.job.core.api.model.Result;
import com.gls.job.core.api.rpc.ExecutorApi;
import com.gls.job.core.exception.JobException;
import com.gls.job.core.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Controller
@RequestMapping("/joblog")
public class JobLogController {
    private static Logger logger = LoggerFactory.getLogger(JobLogController.class);
    @Resource
    public JobInfoDao jobInfoDao;
    @Resource
    public JobLogDao jobLogDao;
    @Resource
    private JobGroupDao jobGroupDao;
    @Resource
    private JobScheduler jobScheduler;
    @Resource
    private JobCompleter jobCompleter;
    @Resource
    private JobInfoController jobInfoController;
    @Resource
    private I18nHelper i18nHelper;

    @RequestMapping
    public String index(HttpServletRequest request, Model model, @RequestParam(required = false, defaultValue = "0") Long jobId) {

        // 执行器列表
        List<JobGroup> jobGroupList_all = jobGroupDao.findAll();

        // filter group
        List<JobGroup> jobGroupList = jobInfoController.filterJobGroupByRole(request, jobGroupList_all);
        if (jobGroupList == null || jobGroupList.size() == 0) {
            throw new JobException(i18nHelper.getString("jobgroup_empty"));
        }

        model.addAttribute("JobGroupList", jobGroupList);

        // 任务
        if (jobId > 0) {
            JobInfo jobInfo = jobInfoDao.loadById(jobId);
            if (jobInfo == null) {
                throw new RuntimeException(i18nHelper.getString("job_info_field_id") + i18nHelper.getString("system_unvalid"));
            }

            model.addAttribute("jobInfo", jobInfo);

            // valid permission
            jobInfoController.validPermission(request, jobInfo.getJobGroup());
        }

        return "joblog/joblog.index";
    }

    @RequestMapping("/getJobsByGroup")
    @ResponseBody
    public Result<List<JobInfo>> getJobsByGroup(Long jobGroup) {
        List<JobInfo> list = jobInfoDao.getJobsByGroup(jobGroup);
        return new Result<List<JobInfo>>(list);
    }

    @RequestMapping("/pageList")
    @ResponseBody
    public Map<String, Object> pageList(HttpServletRequest request,
                                        @RequestParam(required = false, defaultValue = "0") int start,
                                        @RequestParam(required = false, defaultValue = "10") int length,
                                        Long jobGroup, Long jobId, int logStatus, String filterTime) {

        // valid permission
        jobInfoController.validPermission(request, jobGroup);    // 仅管理员支持查询全部；普通用户仅支持查询有权限的 jobGroup

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
        List<JobLog> list = jobLogDao.pageList(start, length, jobGroup, jobId, triggerTimeStart, triggerTimeEnd, logStatus);
        int list_count = jobLogDao.pageListCount(start, length, jobGroup, jobId, triggerTimeStart, triggerTimeEnd, logStatus);

        // package result
        Map<String, Object> maps = new HashMap<String, Object>();
        maps.put("recordsTotal", list_count);        // 总记录数
        maps.put("recordsFiltered", list_count);    // 过滤后的总记录数
        maps.put("data", list);                    // 分页列表
        return maps;
    }

    @RequestMapping("/logDetailPage")
    public String logDetailPage(Long id, Model model) {

        // base check
        Result<String> logStatue = Result.SUCCESS;
        JobLog jobLog = jobLogDao.load(id);
        if (jobLog == null) {
            throw new RuntimeException(i18nHelper.getString("joblog_logid_unvalid"));
        }

        model.addAttribute("triggerCode", jobLog.getTriggerCode());
        model.addAttribute("handleCode", jobLog.getHandleCode());
        model.addAttribute("executorAddress", jobLog.getExecutorAddress());
        model.addAttribute("triggerTime", jobLog.getTriggerTime().getTime());
        model.addAttribute("logId", jobLog.getId());
        return "joblog/joblog.detail";
    }

    @RequestMapping("/logDetailCat")
    @ResponseBody
    public Result<LogResultModel> logDetailCat(String executorAddress, Date triggerTime, Long logId, int fromLineNum) {
        try {
            ExecutorApi executorApi = jobScheduler.getExecutorBiz(executorAddress);
            Result<LogResultModel> logResult = executorApi.log(new LogModel(triggerTime, logId, fromLineNum));

            // is end
            if (logResult.getContent() != null && logResult.getContent().getFromLineNum() > logResult.getContent().getToLineNum()) {
                JobLog jobLog = jobLogDao.load(logId);
                if (jobLog.getHandleCode() > 0) {
                    logResult.getContent().setIsEnd(true);
                }
            }

            return logResult;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result<LogResultModel>(Result.FAIL_CODE, e.getMessage());
        }
    }

    @RequestMapping("/logKill")
    @ResponseBody
    public Result<String> logKill(Long id) {
        // base check
        JobLog log = jobLogDao.load(id);
        JobInfo jobInfo = jobInfoDao.loadById(log.getJobId());
        if (jobInfo == null) {
            return new Result<String>(500, i18nHelper.getString("job_info_glue_jobid_unvalid"));
        }
        if (Result.SUCCESS_CODE != log.getTriggerCode()) {
            return new Result<String>(500, i18nHelper.getString("joblog_kill_log_limit"));
        }

        // request of kill
        Result<String> runResult = null;
        try {
            ExecutorApi executorApi = jobScheduler.getExecutorBiz(log.getExecutorAddress());
            runResult = executorApi.kill(new KillModel(jobInfo.getId()));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            runResult = new Result<String>(500, e.getMessage());
        }

        if (Result.SUCCESS_CODE == runResult.getCode()) {
            log.setHandleCode(Result.FAIL_CODE);
            log.setHandleMsg(i18nHelper.getString("joblog_kill_log_byman") + ":" + (runResult.getMsg() != null ? runResult.getMsg() : ""));
            log.setHandleTime(new Date());
            jobCompleter.updateHandleInfoAndFinish(log);
            return new Result<String>(runResult.getMsg());
        } else {
            return new Result<String>(500, runResult.getMsg());
        }
    }

    @RequestMapping("/clearLog")
    @ResponseBody
    public Result<String> clearLog(Long jobGroup, Long jobId, int type) {

        Date clearBeforeTime = null;
        int clearBeforeNum = 0;
        if (type == 1) {
            clearBeforeTime = DateUtil.addMonths(new Date(), -1);    // 清理一个月之前日志数据
        } else if (type == 2) {
            clearBeforeTime = DateUtil.addMonths(new Date(), -3);    // 清理三个月之前日志数据
        } else if (type == 3) {
            clearBeforeTime = DateUtil.addMonths(new Date(), -6);    // 清理六个月之前日志数据
        } else if (type == 4) {
            clearBeforeTime = DateUtil.addYears(new Date(), -1);    // 清理一年之前日志数据
        } else if (type == 5) {
            clearBeforeNum = 1000;        // 清理一千条以前日志数据
        } else if (type == 6) {
            clearBeforeNum = 10000;        // 清理一万条以前日志数据
        } else if (type == 7) {
            clearBeforeNum = 30000;        // 清理三万条以前日志数据
        } else if (type == 8) {
            clearBeforeNum = 100000;    // 清理十万条以前日志数据
        } else if (type == 9) {
            clearBeforeNum = 0;            // 清理所有日志数据
        } else {
            return new Result<String>(Result.FAIL_CODE, i18nHelper.getString("joblog_clean_type_unvalid"));
        }

        List<Long> logIds = null;
        do {
            logIds = jobLogDao.findClearLogIds(jobGroup, jobId, clearBeforeTime, clearBeforeNum, 1000);
            if (logIds != null && logIds.size() > 0) {
                jobLogDao.clearLog(logIds);
            }
        } while (logIds != null && logIds.size() > 0);

        return Result.SUCCESS;
    }

}
