package com.gls.job.admin.web.controller;

import com.gls.job.admin.core.util.I18nUtil;
import com.gls.job.admin.web.entity.JobInfoEntity;
import com.gls.job.admin.web.entity.JobLogGlueEntity;
import com.gls.job.admin.web.repository.JobInfoRepository;
import com.gls.job.admin.web.repository.JobLogGlueRepository;
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
    private JobInfoRepository jobInfoRepository;
    @Resource
    private JobLogGlueRepository jobLogGlueRepository;

    @RequestMapping
    public String index(HttpServletRequest request, Model model, int jobId) {
        JobInfoEntity jobInfoEntity = jobInfoRepository.loadById(jobId);
        List<JobLogGlueEntity> jobLogGlueEntities = jobLogGlueRepository.findByJobId(jobId);

        if (jobInfoEntity == null) {
            throw new RuntimeException(I18nUtil.getString("job_info_glue_job_id_un_valid"));
        }
        if (GlueTypeEnum.BEAN == GlueTypeEnum.match(jobInfoEntity.getGlueType())) {
            throw new RuntimeException(I18nUtil.getString("job_info_glue_glue_type_un_valid"));
        }

        // valid permission
        JobInfoController.validPermission(request, jobInfoEntity.getJobGroup());

        // Glue类型-字典
        model.addAttribute("GlueTypeEnum", GlueTypeEnum.values());

        model.addAttribute("jobInfo", jobInfoEntity);
        model.addAttribute("jobLogGlues", jobLogGlueEntities);
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
        JobInfoEntity existsJobInfoEntity = jobInfoRepository.loadById(id);
        if (existsJobInfoEntity == null) {
            return new ReturnT<>(500, I18nUtil.getString("job_info_glue_job_id_un_valid"));
        }

        // update new code
        existsJobInfoEntity.setGlueSource(glueSource);
        existsJobInfoEntity.setGlueRemark(glueRemark);
        existsJobInfoEntity.setGlueUpdatetime(new Date());

        existsJobInfoEntity.setUpdateTime(new Date());
        jobInfoRepository.update(existsJobInfoEntity);

        // log old code
        JobLogGlueEntity jobLogGlueEntity = new JobLogGlueEntity();
        jobLogGlueEntity.setJobId(existsJobInfoEntity.getId());
        jobLogGlueEntity.setGlueType(existsJobInfoEntity.getGlueType());
        jobLogGlueEntity.setGlueSource(glueSource);
        jobLogGlueEntity.setGlueRemark(glueRemark);

        jobLogGlueEntity.setAddTime(new Date());
        jobLogGlueEntity.setUpdateTime(new Date());
        jobLogGlueRepository.save(jobLogGlueEntity);

        // remove code backup more than 30
        jobLogGlueRepository.removeOld(existsJobInfoEntity.getId(), 30);

        return ReturnT.SUCCESS;
    }

}
