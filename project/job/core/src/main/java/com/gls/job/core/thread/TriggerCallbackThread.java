package com.gls.job.core.thread;

import com.gls.job.core.api.model.CallbackModel;
import com.gls.job.core.api.model.Result;
import com.gls.job.core.api.rpc.AdminApi;
import com.gls.job.core.context.XxlJobContext;
import com.gls.job.core.context.XxlJobHelper;
import com.gls.job.core.enums.RegistryConfig;
import com.gls.job.core.executor.XxlJobExecutor;
import com.gls.job.core.log.XxlJobFileAppender;
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
 * Created by george on 16/7/22.
 */
public class TriggerCallbackThread {
    private static Logger logger = LoggerFactory.getLogger(TriggerCallbackThread.class);

    private static TriggerCallbackThread instance = new TriggerCallbackThread();
    private static String failCallbackFilePath = XxlJobFileAppender.getLogPath().concat(File.separator).concat("callbacklog").concat(File.separator);
    private static String failCallbackFileName = failCallbackFilePath.concat("gls-job-callback-{x}").concat(".log");
    /**
     * job results callback queue
     */
    private LinkedBlockingQueue<CallbackModel> callBackQueue = new LinkedBlockingQueue<CallbackModel>();
    /**
     * callback thread
     */
    private Thread triggerCallbackThread;
    private Thread triggerRetryCallbackThread;
    private volatile boolean toStop = false;

    public static TriggerCallbackThread getInstance() {
        return instance;
    }

    public static void pushCallBack(CallbackModel callback) {
        getInstance().callBackQueue.add(callback);
        logger.debug(">>>>>>>>>>> gls-job, push callback request, logId:{}", callback.getLogId());
    }

    public void start() {

        // valid
        if (XxlJobExecutor.getAdminApiList() == null) {
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
                        CallbackModel callback = getInstance().callBackQueue.take();
                        if (callback != null) {

                            // callback list param
                            List<CallbackModel> callbackModelList = new ArrayList<CallbackModel>();
                            int drainToNum = getInstance().callBackQueue.drainTo(callbackModelList);
                            callbackModelList.add(callback);

                            // callback, will retry if error
                            if (callbackModelList != null && callbackModelList.size() > 0) {
                                doCallback(callbackModelList);
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
                    List<CallbackModel> callbackModelList = new ArrayList<CallbackModel>();
                    int drainToNum = getInstance().callBackQueue.drainTo(callbackModelList);
                    if (callbackModelList != null && callbackModelList.size() > 0) {
                        doCallback(callbackModelList);
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
     * @param callbackModelList
     */
    private void doCallback(List<CallbackModel> callbackModelList) {
        boolean callbackRet = false;
        // callback, will retry if error
        for (AdminApi adminApi : XxlJobExecutor.getAdminApiList()) {
            try {
                Result<String> callbackResult = adminApi.callback(callbackModelList);
                if (callbackResult != null && Result.SUCCESS_CODE == callbackResult.getCode()) {
                    callbackLog(callbackModelList, "<br>----------- gls-job job callback finish.");
                    callbackRet = true;
                    break;
                } else {
                    callbackLog(callbackModelList, "<br>----------- gls-job job callback fail, callbackResult:" + callbackResult);
                }
            } catch (Exception e) {
                callbackLog(callbackModelList, "<br>----------- gls-job job callback error, errorMsg:" + e.getMessage());
            }
        }
        if (!callbackRet) {
            appendFailCallbackFile(callbackModelList);
        }
    }

    /**
     * callback log
     */
    private void callbackLog(List<CallbackModel> callbackModelList, String logContent) {
        for (CallbackModel callbackModel : callbackModelList) {
            String logFileName = XxlJobFileAppender.makeLogFileName(new Date(callbackModel.getLogDateTime()), callbackModel.getLogId());
            XxlJobContext.setXxlJobContext(new XxlJobContext(
                    -1,
                    null,
                    logFileName,
                    -1,
                    -1));
            XxlJobHelper.log(logContent);
        }
    }

    private void appendFailCallbackFile(List<CallbackModel> callbackModelList) {
        // valid
        if (callbackModelList == null || callbackModelList.size() == 0) {
            return;
        }

        // append file
        byte[] callbackModelList_bytes = JdkSerializeTool.serialize(callbackModelList);

        File callbackLogFile = new File(failCallbackFileName.replace("{x}", String.valueOf(System.currentTimeMillis())));
        if (callbackLogFile.exists()) {
            for (int i = 0; i < 100; i++) {
                callbackLogFile = new File(failCallbackFileName.replace("{x}", String.valueOf(System.currentTimeMillis()).concat("-").concat(String.valueOf(i))));
                if (!callbackLogFile.exists()) {
                    break;
                }
            }
        }
        FileUtil.writeFileContent(callbackLogFile, callbackModelList_bytes);
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
            byte[] callbackModelList_bytes = FileUtil.readFileContent(callbaclLogFile);

            // avoid empty file
            if (callbackModelList_bytes == null || callbackModelList_bytes.length < 1) {
                callbaclLogFile.delete();
                continue;
            }

            List<CallbackModel> callbackModelList = (List<CallbackModel>) JdkSerializeTool.deserialize(callbackModelList_bytes, List.class);

            callbaclLogFile.delete();
            doCallback(callbackModelList);
        }

    }

}
