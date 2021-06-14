package com.gls.job.admin.core.thread;

import com.gls.job.admin.core.constants.JobAdminProperties;
import com.gls.job.admin.core.enums.MisfireStrategy;
import com.gls.job.admin.core.enums.TriggerType;
import com.gls.job.admin.core.ring.RingDataHolder;
import com.gls.job.admin.web.dao.JobInfoDao;
import com.gls.job.admin.web.model.JobInfo;
import com.gls.job.admin.web.service.JobSchedulerService;
import com.gls.job.admin.web.service.JobTriggerService;
import com.gls.job.core.base.BaseThread;
import com.gls.job.core.constants.JobConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author george
 */
@Slf4j
@Component
public class JobScheduleThread extends BaseThread {

    @Resource
    private JobInfoDao jobInfoDao;

    @Resource
    private DataSource dataSource;
    @Resource
    private JobAdminProperties jobAdminProperties;
    @Resource
    private RingDataHolder ringDataHolder;
    @Resource
    private JobTriggerService jobTriggerService;
    @Resource
    private JobSchedulerService jobSchedulerService;

    private int preReadCount;
    private long start;
    private boolean preReadSuc;

    @Override
    protected void initExecute() throws Exception {
        TimeUnit.MILLISECONDS.sleep(5000 - System.currentTimeMillis() % 1000);

        log.info(">>>>>>>>> init gls-job admin scheduler success.");

        // pre-read count: treadpool-size * trigger-qps (each trigger cost 50ms, qps = 1000/50 = 20)
        preReadCount = (jobAdminProperties.getTriggerPoolFastMax() + jobAdminProperties.getTriggerPoolSlowMax()) * 20;
    }

    @Override
    protected void doExecute() throws Exception {
        // Scan Job
        start = System.currentTimeMillis();
        preReadSuc = true;
        Connection conn = null;
        Boolean connAutoCommit = null;
        PreparedStatement preparedStatement = null;
        try {
            conn = dataSource.getConnection();
            connAutoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement("select * from job_lock where lock_name = 'schedule_lock' for update");
            preparedStatement.execute();

            // tx start
            // 1、pre read
            long nowTime = System.currentTimeMillis();
            List<JobInfo> scheduleList = jobInfoDao.scheduleJobQuery(nowTime + JobConstants.PRE_READ_MS, preReadCount);
            if (scheduleList != null && scheduleList.size() > 0) {
                // 2、push time-ring
                for (JobInfo jobInfo : scheduleList) {
                    // time-ring jump
                    if (nowTime > jobInfo.getTriggerNextTime() + JobConstants.PRE_READ_MS) {
                        // 2.1、trigger-expire > 5s：pass && make next-trigger-time
                        log.warn(">>>>>>>>>>> gls-job, schedule misfire, jobId = " + jobInfo.getId());
                        // 1、misfire match
                        MisfireStrategy misfireStrategy = jobInfo.getMisfireStrategy();
                        if (MisfireStrategy.FIRE_ONCE_NOW == misfireStrategy) {
                            // FIRE_ONCE_NOW 》 trigger
                            jobTriggerService.trigger(jobInfo.getId(), TriggerType.MISFIRE, -1, null, null, null);
                            log.debug(">>>>>>>>>>> gls-job, schedule push trigger : jobId = " + jobInfo.getId());
                        }
                        // 2、fresh next
                        refreshNextValidTime(jobInfo, new Date());
                    } else if (nowTime > jobInfo.getTriggerNextTime()) {
                        // 2.2、trigger-expire < 5s：direct-trigger && make next-trigger-time
                        // 1、trigger
                        jobTriggerService.trigger(jobInfo.getId(), TriggerType.CRON, -1, null, null, null);
                        log.debug(">>>>>>>>>>> gls-job, schedule push trigger : jobId = " + jobInfo.getId());
                        // 2、fresh next
                        refreshNextValidTime(jobInfo, new Date());
                        // next-trigger-time in 5s, pre-read again
                        if (jobInfo.getTriggerStatus() == 1 && nowTime + JobConstants.PRE_READ_MS > jobInfo.getTriggerNextTime()) {
                            // 1、make ring second
                            int ringSecond = (int) ((jobInfo.getTriggerNextTime() / 1000) % 60);
                            // 2、push time ring
                            pushTimeRing(ringSecond, jobInfo.getId());
                            // 3、fresh next
                            refreshNextValidTime(jobInfo, new Date(jobInfo.getTriggerNextTime()));
                        }

                    } else {
                        // 2.3、trigger-pre-read：time-ring trigger && make next-trigger-time
                        // 1、make ring second
                        int ringSecond = (int) ((jobInfo.getTriggerNextTime() / 1000) % 60);
                        // 2、push time ring
                        pushTimeRing(ringSecond, jobInfo.getId());
                        // 3、fresh next
                        refreshNextValidTime(jobInfo, new Date(jobInfo.getTriggerNextTime()));
                    }

                }
                // 3、update trigger info
                for (JobInfo jobInfo : scheduleList) {
                    jobInfoDao.scheduleUpdate(jobInfo);
                }
            } else {
                preReadSuc = false;
            }
            // tx stop
        } catch (Exception e) {
            log.error(">>>>>>>>>>> gls-job, JobScheduleHelper#scheduleThread error:{}", e);
        } finally {
            // commit
            if (conn != null) {
                try {
                    conn.commit();
                    conn.setAutoCommit(connAutoCommit);
                    conn.close();
                } catch (SQLException e) {
                    log.error(e.getMessage(), e);
                }
            }
            // close PreparedStatement
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    @Override
    protected void sleepExecute() throws Exception {
        long cost = System.currentTimeMillis() - start;
        // Wait seconds, align second
        if (cost < 1000) {
            // scan-overtime, not wait
            // pre-read period: success > scan each second; fail > skip this period;
            TimeUnit.MILLISECONDS.sleep((preReadSuc ? 1000 : JobConstants.PRE_READ_MS) - System.currentTimeMillis() % 1000);
        }
    }

    @Override
    protected void destroyExecute() throws Exception {
        log.info(">>>>>>>>>>> gls-job, JobScheduleHelper#scheduleThread stop");
    }

    private void pushTimeRing(int ringSecond, Long jobId) {
        // push async ring
        List<Long> ringItemData = ringDataHolder.load(ringSecond);
        if (ringItemData == null) {
            ringItemData = new ArrayList<Long>();
            ringDataHolder.regist(ringSecond, ringItemData, "");
        }
        ringItemData.add(jobId);

        log.debug(">>>>>>>>>>> gls-job, schedule push time-ring : " + ringSecond + " = " + Collections.singletonList(ringItemData));
    }

    private void refreshNextValidTime(JobInfo jobInfo, Date date) throws ParseException {
        Date nextValidTime = jobSchedulerService.generateNextValidTime(jobInfo, date);
        if (nextValidTime != null) {
            jobInfo.setTriggerLastTime(jobInfo.getTriggerNextTime());
            jobInfo.setTriggerNextTime(nextValidTime.getTime());
        } else {
            jobInfo.setTriggerStatus(0);
            jobInfo.setTriggerLastTime(0);
            jobInfo.setTriggerNextTime(0);
            log.warn(">>>>>>>>>>> gls-job, refreshNextValidTime fail for job: jobId={}, scheduleType={}, scheduleConf={}",
                    jobInfo.getId(), jobInfo.getScheduleType(), jobInfo.getScheduleConf());
        }
    }

}
