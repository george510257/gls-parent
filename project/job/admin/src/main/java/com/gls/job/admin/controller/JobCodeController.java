package com.gls.job.admin.controller;

import com.gls.job.admin.core.model.JobInfo;
import com.gls.job.admin.core.model.JobLogGlue;
import com.gls.job.admin.core.util.I18nUtil;
import com.gls.job.admin.dao.JobInfoDao;
import com.gls.job.admin.dao.JobLogGlueDao;
import com.gls.job.core.biz.model.ReturnT;
import com.gls.job.core.glue.GlueTypeEnum;
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

    @RequestMapping
    public String index(HttpServletRequest request, Model model, int jobId) {
        JobInfo jobInfo = jobInfoDao.loadById(jobId);
        List<JobLogGlue> jobLogGlues = jobLogGlueDao.findByJobId(jobId);

        if (jobInfo == null) {
            throw new RuntimeException(I18nUtil.getString("job_info_glue_job_id_un_valid"));
        }
        if (GlueTypeEnum.BEAN == GlueTypeEnum.match(jobInfo.getGlueType())) {
            throw new RuntimeException(I18nUtil.getString("job_info_glue_glue_type_un_valid"));
        }

        // valid permission
        JobInfoController.validPermission(request, jobInfo.getJobGroup());

        // Glue类型-字典
        model.addAttribute("GlueTypeEnum", GlueTypeEnum.values());

        model.addAttribute("jobInfo", jobInfo);
        model.addAttribute("jobLogGlues", jobLogGlues);
        return "jobcode/jobcode.index";
    }

    @RequestMapping("/save")
    @ResponseBody
    public ReturnT<String> save(int id, String glueSource, String glueRemark) {
        // valid
        if (glueRemark == null) {
            return new ReturnT<>(500, (I18nUtil.getString("system_please_input") + I18nUtil.getString("job_info_glue_remark")));
        }
        if (glueRemark.length() < 4 || glueRemark.length() > 100) {
            return new ReturnT<>(500, I18nUtil.getString("job_info_glue_remark_limit"));
        }
        JobInfo existsJobInfo = jobInfoDao.loadById(id);
        if (existsJobInfo == null) {
            return new ReturnT<>(500, I18nUtil.getString("job_info_glue_job_id_un_valid"));
        }

        // update new code
        existsJobInfo.setGlueSource(glueSource);
        existsJobInfo.setGlueRemark(glueRemark);
        existsJobInfo.setGlueUpdatetime(new Date());

        existsJobInfo.setUpdateTime(new Date());
        jobInfoDao.update(existsJobInfo);

        // log old code
        JobLogGlue jobLogGlue = new JobLogGlue();
        jobLogGlue.setJobId(existsJobInfo.getId());
        jobLogGlue.setGlueType(existsJobInfo.getGlueType());
        jobLogGlue.setGlueSource(glueSource);
        jobLogGlue.setGlueRemark(glueRemark);

        jobLogGlue.setAddTime(new Date());
        jobLogGlue.setUpdateTime(new Date());
        jobLogGlueDao.save(jobLogGlue);

        // remove code backup more than 30
        jobLogGlueDao.removeOld(existsJobInfo.getId(), 30);

        return ReturnT.SUCCESS;
    }

}
