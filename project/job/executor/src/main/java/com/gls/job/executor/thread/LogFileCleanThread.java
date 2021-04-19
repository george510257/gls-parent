package com.gls.job.executor.thread;

import com.gls.job.core.log.XxlJobFileAppender;
import com.gls.job.core.util.FileUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author george
 */
@Slf4j
public class LogFileCleanThread extends Thread {

    private final long logRetentionDays;
    @Setter
    private volatile boolean toStop = false;

    public LogFileCleanThread(long logRetentionDays) {
        this.logRetentionDays = logRetentionDays;
    }

    @Override
    public void run() {
        while (!toStop) {
            try {
                // clean log dir, over logRetentionDays
                File[] childDirs = new File(XxlJobFileAppender.getLogPath()).listFiles();
                if (childDirs != null && childDirs.length > 0) {

                    // today
                    Calendar todayCal = Calendar.getInstance();
                    todayCal.set(Calendar.HOUR_OF_DAY, 0);
                    todayCal.set(Calendar.MINUTE, 0);
                    todayCal.set(Calendar.SECOND, 0);
                    todayCal.set(Calendar.MILLISECOND, 0);

                    Date todayDate = todayCal.getTime();

                    for (File childFile : childDirs) {

                        // valid
                        if (!childFile.isDirectory()) {
                            continue;
                        }
                        if (!childFile.getName().contains("-")) {
                            continue;
                        }

                        // file create date
                        Date logFileCreateDate = null;
                        try {
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            logFileCreateDate = simpleDateFormat.parse(childFile.getName());
                        } catch (ParseException e) {
                            log.error(e.getMessage(), e);
                        }
                        if (logFileCreateDate == null) {
                            continue;
                        }

                        if ((todayDate.getTime() - logFileCreateDate.getTime()) >= logRetentionDays * (24 * 60 * 60 * 1000)) {
                            FileUtil.deleteRecursively(childFile);
                        }

                    }
                }

            } catch (Exception e) {
                if (!toStop) {
                    log.error(e.getMessage(), e);
                }

            }

            try {
                TimeUnit.DAYS.sleep(1);
            } catch (InterruptedException e) {
                if (!toStop) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        log.info(">>>>>>>>>>> gls-job, executor JobLogFileCleanThread thread destroy.");
    }
}
