package com.gls.job.admin.core.thread;

import com.gls.job.admin.core.cron.CronExpression;
import com.gls.job.admin.core.holder.RingDataHolder;
import com.gls.job.admin.core.server.JobScheduleServer;
import com.gls.job.admin.core.server.JobTriggerPoolHelper;
import com.gls.job.admin.web.dao.JobInfoDao;
import com.gls.job.admin.web.entity.JobInfo;
import com.gls.job.admin.web.entity.enums.MisfireStrategy;
import com.gls.job.admin.web.entity.enums.ScheduleType;
import com.gls.job.admin.web.entity.enums.TriggerType;
import com.gls.job.core.base.thread.BaseThread;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author george
 */
@Slf4j
public class JobScheduleThread extends BaseThread {

    private int preReadCount;

    private long start;

    private boolean preReadSuc = true;

    @Value("${gls.job.triggerpool.fast.max}")
    private int triggerPoolFastMax;
    @Value("${gls.job.triggerpool.slow.max}")
    private int triggerPoolSlowMax;

    @Resource
    private DataSource dataSource;

    @Resource
    private JobInfoDao jobInfoDao;

    public static Date generateNextValidTime(JobInfo jobInfo, Date fromTime) throws Exception {
        ScheduleType scheduleTypeEnum = jobInfo.getScheduleType();
        if (ScheduleType.CRON == scheduleTypeEnum) {
            return new CronExpression(jobInfo.getScheduleConf()).getNextValidTimeAfter(fromTime);
        } else if (ScheduleType.FIX_RATE == scheduleTypeEnum) {
            return new Date(fromTime.getTime() + Integer.parseInt(jobInfo.getScheduleConf()) * 1000L);
        }
        return null;
    }

    @Override
    protected void initExecute() throws Exception {
        TimeUnit.MILLISECONDS.sleep(5000 - System.currentTimeMillis() % 1000);
        log.info(">>>>>>>>> init gls-job admin scheduler success.");
        // pre-read count: treadpool-size * trigger-qps (each trigger cost 50ms, qps = 1000/50 = 20)
        preReadCount = (triggerPoolFastMax + triggerPoolSlowMax) * 20;
    }

    @Override
    protected void doExecute() throws Exception {

        // Scan Job
        start = System.currentTimeMillis();

        Connection conn = null;
        Boolean connAutoCommit = null;
        PreparedStatement preparedStatement = null;

        preReadSuc = true;
        try {

            conn = dataSource.getConnection();
            connAutoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);

            preparedStatement = conn.prepareStatement("select * from gls_job_lock where lock_name = 'schedule_lock' for update");
            preparedStatement.execute();

            // tx start

            // 1、pre read
            long nowTime = System.currentTimeMillis();
            List<JobInfo> scheduleList = jobInfoDao.scheduleJobQuery(nowTime + JobScheduleServer.PRE_READ_MS, preReadCount);
            if (scheduleList != null && scheduleList.size() > 0) {
                // 2、push time-ring
                for (JobInfo jobInfo : scheduleList) {

                    // time-ring jump
                    if (nowTime > jobInfo.getTriggerNextTime() + JobScheduleServer.PRE_READ_MS) {
                        // 2.1、trigger-expire > 5s：pass && make next-trigger-time
                        log.warn(">>>>>>>>>>> gls-job, schedule misfire, jobId = " + jobInfo.getId());

                        // 1、misfire match
                        MisfireStrategy misfireStrategyEnum = jobInfo.getMisfireStrategy();
                        if (MisfireStrategy.FIRE_ONCE_NOW == misfireStrategyEnum) {
                            // FIRE_ONCE_NOW 》 trigger
                            JobTriggerPoolHelper.trigger(jobInfo.getId(), TriggerType.MISFIRE, -1, null, null, null);
                            log.debug(">>>>>>>>>>> gls-job, schedule push trigger : jobId = " + jobInfo.getId());
                        }

                        // 2、fresh next
                        refreshNextValidTime(jobInfo, new Date());

                    } else if (nowTime > jobInfo.getTriggerNextTime()) {
                        // 2.2、trigger-expire < 5s：direct-trigger && make next-trigger-time

                        // 1、trigger
                        JobTriggerPoolHelper.trigger(jobInfo.getId(), TriggerType.CRON, -1, null, null, null);
                        log.debug(">>>>>>>>>>> gls-job, schedule push trigger : jobId = " + jobInfo.getId());

                        // 2、fresh next
                        refreshNextValidTime(jobInfo, new Date());

                        // next-trigger-time in 5s, pre-read again
                        if (jobInfo.getTriggerStatus() == 1 && nowTime + JobScheduleServer.PRE_READ_MS > jobInfo.getTriggerNextTime()) {

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
            if (!isStop()) {
                log.error(">>>>>>>>>>> gls-job, JobScheduleHelper#scheduleThread error:{}", e);
            }
        } finally {

            // commit
            if (conn != null) {
                try {
                    conn.commit();
                } catch (SQLException e) {
                    if (!isStop()) {
                        log.error(e.getMessage(), e);
                    }
                }
                try {
                    conn.setAutoCommit(connAutoCommit);
                } catch (SQLException e) {
                    if (!isStop()) {
                        log.error(e.getMessage(), e);
                    }
                }
                try {
                    conn.close();
                } catch (SQLException e) {
                    if (!isStop()) {
                        log.error(e.getMessage(), e);
                    }
                }
            }

            // close PreparedStatement
            if (null != preparedStatement) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    if (!isStop()) {
                        log.error(e.getMessage(), e);
                    }
                }
            }
        }

    }

    @Override
    protected void sleepExecute() throws Exception {
        long cost = System.currentTimeMillis() - start;

        // Wait seconds, align second
        if (cost < 1000) {  // scan-overtime, not wait
            try {
                // pre-read period: success > scan each second; fail > skip this period;
                TimeUnit.MILLISECONDS.sleep((preReadSuc ? 1000 : JobScheduleServer.PRE_READ_MS) - System.currentTimeMillis() % 1000);
            } catch (InterruptedException e) {
                if (!isStop()) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    @Override
    protected void destroyExecute() throws Exception {
        log.info(">>>>>>>>>>> gls-job, JobScheduleHelper#scheduleThread stop");
    }

    private void pushTimeRing(int ringSecond, Long jobId) {
        // push async ring
        List<Long> ringItemData = RingDataHolder.getInstance().computeIfAbsent(ringSecond, k -> new ArrayList<>());
        ringItemData.add(jobId);

        log.debug(">>>>>>>>>>> gls-job, schedule push time-ring : " + ringSecond + " = " + Collections.singletonList(ringItemData));
    }

    private void refreshNextValidTime(JobInfo jobInfo, Date fromTime) throws Exception {
        Date nextValidTime = generateNextValidTime(jobInfo, fromTime);
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
