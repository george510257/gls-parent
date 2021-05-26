package com.gls.job.core.thread;

import com.gls.job.core.biz.AdminBiz;
import com.gls.job.core.biz.model.HandleCallbackParam;
import com.gls.job.core.biz.model.ReturnT;
import com.gls.job.core.context.JobContext;
import com.gls.job.core.context.JobHelper;
import com.gls.job.core.enums.RegistryConfig;
import com.gls.job.core.executor.JobExecutor;
import com.gls.job.core.log.JobFileAppender;
import com.gls.job.core.util.FileUtil;
import com.gls.job.core.util.JdkSerializeTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by xuxueli on 16/7/22.
 */
public class TriggerCallbackThread {
    private static Logger logger = LoggerFactory.getLogger(TriggerCallbackThread.class);

    private static TriggerCallbackThread instance = new TriggerCallbackThread();
    private static String failCallbackFilePath = JobFileAppender.getLogPath().concat(File.separator).concat("callbacklog").concat(File.separator);
    private static String failCallbackFileName = failCallbackFilePath.concat("gls-job-callback-{x}").concat(".log");
    /**
     * job results callback queue
     */
    private LinkedBlockingQueue<HandleCallbackParam> callBackQueue = new LinkedBlockingQueue<HandleCallbackParam>();
    /**
     * callback thread
     */
    private Thread triggerCallbackThread;
    private Thread triggerRetryCallbackThread;
    private volatile boolean toStop = false;

    public static TriggerCallbackThread getInstance() {
        return instance;
    }

    public static void pushCallBack(HandleCallbackParam callback) {
        getInstance().callBackQueue.add(callback);
        logger.debug(">>>>>>>>>>> gls-job, push callback request, logId:{}", callback.getLogId());
    }

    public void start() {

        // valid
        if (JobExecutor.getAdminBizList() == null) {
            logger.warn(">>>>>>>>>>> gls-job, executor callback config fail, adminAddresses is null.");
            return;
        }

        // callback
        triggerCallbackThread = new Thread(new Runnable() {

            @Override
            public void run() {

                // normal callback
                while (!toStop) {
                    try {
                        HandleCallbackParam callback = getInstance().callBackQueue.take();
                        if (callback != null) {

                            // callback list param
                            List<HandleCallbackParam> callbackParamList = new ArrayList<HandleCallbackParam>();
                            int drainToNum = getInstance().callBackQueue.drainTo(callbackParamList);
                            callbackParamList.add(callback);

                            // callback, will retry if error
                            if (callbackParamList != null && callbackParamList.size() > 0) {
                                doCallback(callbackParamList);
                            }
                        }
                    } catch (Exception e) {
                        if (!toStop) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                }

                // last callback
                try {
                    List<HandleCallbackParam> callbackParamList = new ArrayList<HandleCallbackParam>();
                    int drainToNum = getInstance().callBackQueue.drainTo(callbackParamList);
                    if (callbackParamList != null && callbackParamList.size() > 0) {
                        doCallback(callbackParamList);
                    }
                } catch (Exception e) {
                    if (!toStop) {
                        logger.error(e.getMessage(), e);
                    }
                }
                logger.info(">>>>>>>>>>> gls-job, executor callback thread destory.");

            }
        });
        triggerCallbackThread.setDaemon(true);
        triggerCallbackThread.setName("gls-job, executor TriggerCallbackThread");
        triggerCallbackThread.start();

        // retry
        triggerRetryCallbackThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!toStop) {
                    try {
                        retryFailCallbackFile();
                    } catch (Exception e) {
                        if (!toStop) {
                            logger.error(e.getMessage(), e);
                        }

                    }
                    try {
                        TimeUnit.SECONDS.sleep(RegistryConfig.BEAT_TIMEOUT);
                    } catch (InterruptedException e) {
                        if (!toStop) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                }
                logger.info(">>>>>>>>>>> gls-job, executor retry callback thread destory.");
            }
        });
        triggerRetryCallbackThread.setDaemon(true);
        triggerRetryCallbackThread.start();

    }

    public void toStop() {
        toStop = true;
        // stop callback, interrupt and wait
        if (triggerCallbackThread != null) {    // support empty admin address
            triggerCallbackThread.interrupt();
            try {
                triggerCallbackThread.join();
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        }

        // stop retry, interrupt and wait
        if (triggerRetryCallbackThread != null) {
            triggerRetryCallbackThread.interrupt();
            try {
                triggerRetryCallbackThread.join();
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        }

    }

    // ---------------------- fail-callback file ----------------------

    /**
     * do callback, will retry if error
     *
     * @param callbackParamList
     */
    private void doCallback(List<HandleCallbackParam> callbackParamList) {
        boolean callbackRet = false;
        // callback, will retry if error
        for (AdminBiz adminBiz : JobExecutor.getAdminBizList()) {
            try {
                ReturnT<String> callbackResult = adminBiz.callback(callbackParamList);
                if (callbackResult != null && ReturnT.SUCCESS_CODE == callbackResult.getCode()) {
                    callbackLog(callbackParamList, "<br>----------- gls-job job callback finish.");
                    callbackRet = true;
                    break;
                } else {
                    callbackLog(callbackParamList, "<br>----------- gls-job job callback fail, callbackResult:" + callbackResult);
                }
            } catch (Exception e) {
                callbackLog(callbackParamList, "<br>----------- gls-job job callback error, errorMsg:" + e.getMessage());
            }
        }
        if (!callbackRet) {
            appendFailCallbackFile(callbackParamList);
        }
    }

    /**
     * callback log
     */
    private void callbackLog(List<HandleCallbackParam> callbackParamList, String logContent) {
        for (HandleCallbackParam callbackParam : callbackParamList) {
            String logFileName = JobFileAppender.makeLogFileName(new Date(callbackParam.getLogDateTim()), callbackParam.getLogId());
            JobContext.setJobContext(new JobContext(
                    -1,
                    null,
                    logFileName,
                    -1,
                    -1));
            JobHelper.log(logContent);
        }
    }

    private void appendFailCallbackFile(List<HandleCallbackParam> callbackParamList) {
        // valid
        if (callbackParamList == null || callbackParamList.size() == 0) {
            return;
        }

        // append file
        byte[] callbackParamList_bytes = JdkSerializeTool.serialize(callbackParamList);

        File callbackLogFile = new File(failCallbackFileName.replace("{x}", String.valueOf(System.currentTimeMillis())));
        if (callbackLogFile.exists()) {
            for (int i = 0; i < 100; i++) {
                callbackLogFile = new File(failCallbackFileName.replace("{x}", String.valueOf(System.currentTimeMillis()).concat("-").concat(String.valueOf(i))));
                if (!callbackLogFile.exists()) {
                    break;
                }
            }
        }
        FileUtil.writeFileContent(callbackLogFile, callbackParamList_bytes);
    }

    private void retryFailCallbackFile() {

        // valid
        File callbackLogPath = new File(failCallbackFilePath);
        if (!callbackLogPath.exists()) {
            return;
        }
        if (callbackLogPath.isFile()) {
            callbackLogPath.delete();
        }
        if (!(callbackLogPath.isDirectory() && callbackLogPath.list() != null && callbackLogPath.list().length > 0)) {
            return;
        }

        // load and clear file, retry
        for (File callbaclLogFile : callbackLogPath.listFiles()) {
            byte[] callbackParamList_bytes = FileUtil.readFileContent(callbaclLogFile);

            // avoid empty file
            if (callbackParamList_bytes == null || callbackParamList_bytes.length < 1) {
                callbaclLogFile.delete();
                continue;
            }

            List<HandleCallbackParam> callbackParamList = (List<HandleCallbackParam>) JdkSerializeTool.deserialize(callbackParamList_bytes, List.class);

            callbaclLogFile.delete();
            doCallback(callbackParamList);
        }

    }

}