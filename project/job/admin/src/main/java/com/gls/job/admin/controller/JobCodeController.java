package com.gls.job.admin.controller;

import com.gls.job.admin.core.model.XxlJobInfo;
import com.gls.job.admin.core.model.XxlJobLogGlue;
import com.gls.job.admin.core.util.I18nUtil;
import com.gls.job.admin.dao.XxlJobInfoDao;
import com.gls.job.admin.dao.XxlJobLogGlueDao;
import com.gls.job.core.api.model.Result;
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
 * @author george 2015-12-19 16:13:16
 */
@Controller
@RequestMapping("/jobcode")
public class JobCodeController {

    @Resource
    private XxlJobInfoDao glsJobInfoDao;
    @Resource
    private XxlJobLogGlueDao glsJobLogGlueDao;

    @RequestMapping
    public String index(HttpServletRequest request, Model model, int jobId) {
        XxlJobInfo jobInfo = glsJobInfoDao.loadById(jobId);
        List<XxlJobLogGlue> jobLogGlues = glsJobLogGlueDao.findByJobId(jobId);

        if (jobInfo == null) {
            throw new RuntimeException(I18nUtil.getString("jobinfo_glue_jobid_unvalid"));
        }
        if (GlueTypeEnum.BEAN == GlueTypeEnum.match(jobInfo.getGlueType())) {
            throw new RuntimeException(I18nUtil.getString("jobinfo_glue_gluetype_unvalid"));
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
    public Result<String> save(Model model, int id, String glueSource, String glueRemark) {
        // valid
        if (glueRemark == null) {
            return new Result<String>(500, (I18nUtil.getString("system_please_input") + I18nUtil.getString("jobinfo_glue_remark")));
        }
        if (glueRemark.length() < 4 || glueRemark.length() > 100) {
            return new Result<String>(500, I18nUtil.getString("jobinfo_glue_remark_limit"));
        }
        XxlJobInfo exists_jobInfo = glsJobInfoDao.loadById(id);
        if (exists_jobInfo == null) {
            return new Result<String>(500, I18nUtil.getString("jobinfo_glue_jobid_unvalid"));
        }

        // update new code
        exists_jobInfo.setGlueSource(glueSource);
        exists_jobInfo.setGlueRemark(glueRemark);
        exists_jobInfo.setGlueUpdatetime(new Date());

        exists_jobInfo.setUpdateTime(new Date());
        glsJobInfoDao.update(exists_jobInfo);

        // log old code
        XxlJobLogGlue glsJobLogGlue = new XxlJobLogGlue();
        glsJobLogGlue.setJobId(exists_jobInfo.getId());
        glsJobLogGlue.setGlueType(exists_jobInfo.getGlueType());
        glsJobLogGlue.setGlueSource(glueSource);
        glsJobLogGlue.setGlueRemark(glueRemark);

        glsJobLogGlue.setAddTime(new Date());
        glsJobLogGlue.setUpdateTime(new Date());
        glsJobLogGlueDao.save(glsJobLogGlue);

        // remove code backup more than 30
        glsJobLogGlueDao.removeOld(exists_jobInfo.getId(), 30);

        return Result.SUCCESS;
    }

}
