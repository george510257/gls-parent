package com.gls.job.admin.web.service.impl;

import com.gls.job.admin.constants.JobAdminProperties;
import com.gls.job.admin.core.support.JobScheduleHelper;
import com.gls.job.admin.core.support.RingDataHolder;
import com.gls.job.admin.web.model.JobInfo;
import com.gls.job.admin.web.service.AsyncService;
import com.gls.job.admin.web.service.JobInfoService;
import com.gls.job.admin.web.service.JobScheduleService;
import com.gls.job.core.constants.JobConstants;
import com.gls.job.core.constants.MisfireStrategy;
import com.gls.job.core.constants.TriggerType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author george
 */
@Slf4j
@Service
public class JobScheduleServiceImpl implements JobScheduleService {
    @Resource
    private AsyncService asyncService;
    @Resource
    private RingDataHolder ringDataHolder;
    @Resource
    private JobAdminProperties jobAdminProperties;
    @Resource
    private JobInfoService jobInfoService;

    @Override
    public boolean run() {
        int preReadCount = (jobAdminProperties.getTriggerPoolFastMax() + jobAdminProperties.getTriggerPoolFastMax()) * 20;
        // 1.pre read
        long nowTime = System.currentTimeMillis();
        List<JobInfo> jobInfos = jobInfoService.getScheduleJob(new Date(nowTime + JobConstants.PRE_READ_MS), preReadCount);
        if (ObjectUtils.isEmpty(jobInfos)) {
            return false;
        }
        // 2.push time-ring
        for (JobInfo jobInfo : jobInfos) {
            if (nowTime > jobInfo.getTriggerNextTime() + JobConstants.PRE_READ_MS) {
                // 2.1、trigger-expire > 5s：pass && make next-trigger-time
                log.warn(">>>>>>>>>>> gls-job, schedule misfire, jobId = " + jobInfo.getId());
                // 1、misfire match
                if (MisfireStrategy.FIRE_ONCE_NOW.equals(jobInfo.getMisfireStrategy())) {
                    // FIRE_ONCE_NOW 》 trigger
                    asyncService.asyncTrigger(jobInfo.getId(), TriggerType.MISFIRE, -1, null, null, null);
                    log.debug(">>>>>>>>>>> gls-job, schedule push trigger : jobId = " + jobInfo.getId());
                }
                // 2、fresh next
                JobScheduleHelper.refreshNextValidTime(jobInfo, new Date());
            } else if (nowTime > jobInfo.getTriggerNextTime()) {
                // 1、trigger
                asyncService.asyncTrigger(jobInfo.getId(), TriggerType.CRON, -1, null, null, null);
                log.debug(">>>>>>>>>>> gls-job, schedule push trigger : jobId = " + jobInfo.getId());
                // 2、fresh next
                JobScheduleHelper.refreshNextValidTime(jobInfo, new Date());
                // next-trigger-time in 5s, pre-read again
                if (jobInfo.getTriggerStatus() == 1 && nowTime + JobConstants.PRE_READ_MS > jobInfo.getTriggerNextTime()) {
                    // 1、make ring second
                    int ringSecond = (int) ((jobInfo.getTriggerNextTime() / 1000) % 60);
                    // 2、push time ring
                    pushTimeRing(ringSecond, jobInfo.getId());
                    // 3、fresh next
                    JobScheduleHelper.refreshNextValidTime(jobInfo, new Date(jobInfo.getTriggerNextTime()));
                }
            } else {
                // 2.3、trigger-pre-read：time-ring trigger && make next-trigger-time
                // 1、make ring second
                int ringSecond = (int) ((jobInfo.getTriggerNextTime() / 1000) % 60);
                // 2、push time ring
                pushTimeRing(ringSecond, jobInfo.getId());
                // 3、fresh next
                JobScheduleHelper.refreshNextValidTime(jobInfo, new Date(jobInfo.getTriggerNextTime()));
            }
            jobInfoService.update(jobInfo);
        }
        return true;
    }

    @Override
    public void ring() {
        // second data
        List<Long> ringItemData = new ArrayList<>();
        // 避免处理耗时太长，跨过刻度，向前校验一个刻度；
        int nowSecond = Calendar.getInstance().get(Calendar.SECOND);
        for (int i = 0; i < 2; i++) {
            List<Long> tmpData = ringDataHolder.remove((nowSecond + 60 - i) % 60, "");
            if (tmpData != null) {
                ringItemData.addAll(tmpData);
            }
        }
        // ring trigger
        log.debug(">>>>>>>>>>> gls-job, time-ring beat : " + nowSecond + " = " + Collections.singletonList(ringItemData));
        if (ringItemData.size() > 0) {
            // do trigger
            for (Long jobId : ringItemData) {
                // do trigger
                asyncService.asyncTrigger(jobId, TriggerType.CRON, -1, null, null, null);
            }
            // clear
            ringItemData.clear();
        }
    }

    private void pushTimeRing(int ringSecond, Long id) {
        // push async ring
        List<Long> ringItemData = ringDataHolder.load(ringSecond);
        if (ringItemData == null) {
            ringItemData = new ArrayList<>();
            ringDataHolder.regist(ringSecond, ringItemData, "");
        }
        ringItemData.add(id);
        log.debug(">>>>>>>>>>> gls-job, schedule push time-ring : " + ringSecond + " = " + Collections.singletonList(ringItemData));
    }
}
