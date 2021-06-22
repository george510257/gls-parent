package com.gls.job.admin.web.controller;

import com.gls.job.admin.core.i18n.I18nHelper;
import com.gls.job.admin.web.dao.JobInfoDao;
import com.gls.job.admin.web.dao.JobLogGlueDao;
import com.gls.job.admin.web.model.JobInfo;
import com.gls.job.admin.web.model.JobLogGlue;
import com.gls.job.core.api.model.Result;
import com.gls.job.core.api.model.enums.GlueType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * job code controller
 *
 * @author xuxueli 2015-12-19 16:13:16
 */
@Controller
@RequestMapping("/jobcode")
public class JobCodeController {

    @Resource
    private JobInfoDao jobInfoDao;
    @Resource
    private JobLogGlueDao jobLogGlueDao;
    @Resource
    private JobInfoController jobInfoController;
    @Resource
    private I18nHelper i18nHelper;

    @RequestMapping
    public String index(HttpServletRequest request, Model model, Long jobId) {
        JobInfo jobInfo = jobInfoDao.loadById(jobId);
        List<JobLogGlue> jobLogGlues = jobLogGlueDao.findByJobId(jobId);

        if (jobInfo == null) {
            throw new RuntimeException(i18nHelper.getString("job_info_glue_jobid_unvalid"));
        }
        if (GlueType.BEAN == jobInfo.getGlueType()) {
            throw new RuntimeException(i18nHelper.getString("job_info_glue_gluetype_unvalid"));
        }

        // valid permission
        jobInfoController.validPermission(request, jobInfo.getJobGroup());

        // Glue类型-字典
        model.addAttribute("GlueType", GlueType.values());

        model.addAttribute("jobInfo", jobInfo);
        model.addAttribute("jobLogGlues", jobLogGlues);
        return "jobcode/jobcode.index";
    }

    @RequestMapping("/save")
    @ResponseBody
    public Result<String> save(Model model, Long id, String glueSource, String glueRemark) {
        // valid
        if (glueRemark == null) {
            return new Result<String>(500, (i18nHelper.getString("system_please_input") + i18nHelper.getString("job_info_glue_remark")));
        }
        if (glueRemark.length() < 4 || glueRemark.length() > 100) {
            return new Result<String>(500, i18nHelper.getString("job_info_glue_remark_limit"));
        }
        JobInfo exists_jobInfo = jobInfoDao.loadById(id);
        if (exists_jobInfo == null) {
            return new Result<String>(500, i18nHelper.getString("job_info_glue_jobid_unvalid"));
        }

        // update new code
        exists_jobInfo.setGlueSource(glueSource);
        exists_jobInfo.setGlueRemark(glueRemark);
        exists_jobInfo.setGlueUpdateTime(new Date());

        exists_jobInfo.setUpdateTime(new Date());
        jobInfoDao.update(exists_jobInfo);

        // log old code
        JobLogGlue jobLogGlue = new JobLogGlue();
        jobLogGlue.setJobId(exists_jobInfo.getId());
        jobLogGlue.setGlueType(exists_jobInfo.getGlueType());
        jobLogGlue.setGlueSource(glueSource);
        jobLogGlue.setGlueRemark(glueRemark);

        jobLogGlue.setAddTime(new Date());
        jobLogGlue.setUpdateTime(new Date());
        jobLogGlueDao.save(jobLogGlue);

        // remove code backup more than 30
        jobLogGlueDao.removeOld(exists_jobInfo.getId(), 30);

        return Result.SUCCESS;
    }

}
