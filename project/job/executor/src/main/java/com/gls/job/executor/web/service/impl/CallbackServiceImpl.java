package com.gls.job.executor.web.service.impl;

import com.gls.job.core.api.model.CallbackModel;
import com.gls.job.core.api.model.Result;
import com.gls.job.core.api.rpc.AdminApi;
import com.gls.job.core.util.FileUtil;
import com.gls.job.core.util.JdkSerializeTool;
import com.gls.job.executor.core.helper.JobFileHelper;
import com.gls.job.executor.core.helper.JobHelper;
import com.gls.job.executor.core.holder.JobContextHolder;
import com.gls.job.executor.web.model.JobContextModel;
import com.gls.job.executor.web.service.CallbackService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * @author george
 */
@Service
public class CallbackServiceImpl implements CallbackService {

    private static final String FAIL_CALLBACK_FILE_PATH = JobFileHelper.getLogPath().concat(File.separator).concat("callbacklog").concat(File.separator);
    private static final String FAIL_CALLBACK_FILE_NAME = FAIL_CALLBACK_FILE_PATH.concat("gls-job-callback-{x}").concat(".log");
    @DubboReference
    private AdminApi adminApi;

    @Override
    public void doCallback(List<CallbackModel> callbackModelList) {
        boolean callbackRet = false;
        // callback, will retry if error
        try {
            Result<String> callbackResult = adminApi.callback(callbackModelList);
            if (callbackResult != null && Result.SUCCESS_CODE == callbackResult.getCode()) {
                callbackLog(callbackModelList, "<br>----------- gls-job job callback finish.");
                callbackRet = true;
            } else {
                callbackLog(callbackModelList, "<br>----------- gls-job job callback fail, callbackResult:" + callbackResult);
            }
        } catch (Exception e) {
            callbackLog(callbackModelList, "<br>----------- gls-job job callback error, errorMsg:" + e.getMessage());
        }

        if (!callbackRet) {
            appendFailCallbackFile(callbackModelList);
        }
    }

    private void appendFailCallbackFile(List<CallbackModel> callbackModelList) {
        // valid
        if (callbackModelList == null || callbackModelList.size() == 0) {
            return;
        }

        // append file
        byte[] callbackModelListBytes = JdkSerializeTool.serialize(callbackModelList);

        File callbackLogFile = new File(FAIL_CALLBACK_FILE_NAME.replace("{x}", String.valueOf(System.currentTimeMillis())));
        if (callbackLogFile.exists()) {
            for (int i = 0; i < 100; i++) {
                callbackLogFile = new File(FAIL_CALLBACK_FILE_NAME.replace("{x}", String.valueOf(System.currentTimeMillis()).concat("-").concat(String.valueOf(i))));
                if (!callbackLogFile.exists()) {
                    break;
                }
            }
        }
        FileUtil.writeFileContent(callbackLogFile, callbackModelListBytes);
    }

    private void callbackLog(List<CallbackModel> callbackModelList, String logContent) {
        for (CallbackModel callbackModel : callbackModelList) {
            String logFileName = JobFileHelper.makeLogFileName(callbackModel.getLogDateTime(), callbackModel.getLogId());
            JobContextHolder.getInstance().set(new JobContextModel(-1, null, logFileName, -1, -1));
            JobHelper.log(logContent);
        }
    }

    @Override
    public void retryFailCallbackFile() {
        // valid
        File callbackLogPath = new File(FAIL_CALLBACK_FILE_PATH);
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
        for (File callbackLogFile : callbackLogPath.listFiles()) {
            byte[] callbackModelListBytes = FileUtil.readFileContent(callbackLogFile);

            // avoid empty file
            if (callbackModelListBytes == null || callbackModelListBytes.length < 1) {
                callbackLogFile.delete();
                continue;
            }

            List<CallbackModel> callbackModelList = (List<CallbackModel>) JdkSerializeTool.deserialize(callbackModelListBytes, List.class);

            callbackLogFile.delete();
            doCallback(callbackModelList);
        }
    }
}
