package com.gls.job.executor.web.service.impl;

import com.gls.job.core.api.model.CallbackModel;
import com.gls.job.core.api.model.Result;
import com.gls.job.core.api.rpc.AdminApi;
import com.gls.job.core.util.FileUtil;
import com.gls.job.core.util.JdkSerializeTool;
import com.gls.job.executor.config.XxlJobExecutor;
import com.gls.job.executor.context.XxlJobContext;
import com.gls.job.executor.helper.XxlJobFileHelper;
import com.gls.job.executor.helper.XxlJobHelper;
import com.gls.job.executor.web.service.CallbackService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * @author george
 */
@Service
public class CallbackServiceImpl implements CallbackService {

    private static final String FAIL_CALLBACK_FILE_PATH = XxlJobFileHelper.getLogPath().concat(File.separator).concat("callbacklog").concat(File.separator);
    private static final String FAIL_CALLBACK_FILE_NAME = FAIL_CALLBACK_FILE_PATH.concat("gls-job-callback-{x}").concat(".log");

    @Override
    public void doCallback(List<CallbackModel> callbackModelList) {
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

    private void appendFailCallbackFile(List<CallbackModel> callbackModelList) {
        // valid
        if (callbackModelList == null || callbackModelList.size() == 0) {
            return;
        }

        // append file
        byte[] callbackModelList_bytes = JdkSerializeTool.serialize(callbackModelList);

        File callbackLogFile = new File(FAIL_CALLBACK_FILE_NAME.replace("{x}", String.valueOf(System.currentTimeMillis())));
        if (callbackLogFile.exists()) {
            for (int i = 0; i < 100; i++) {
                callbackLogFile = new File(FAIL_CALLBACK_FILE_NAME.replace("{x}", String.valueOf(System.currentTimeMillis()).concat("-").concat(String.valueOf(i))));
                if (!callbackLogFile.exists()) {
                    break;
                }
            }
        }
        FileUtil.writeFileContent(callbackLogFile, callbackModelList_bytes);
    }

    private void callbackLog(List<CallbackModel> callbackModelList, String logContent) {
        for (CallbackModel callbackModel : callbackModelList) {
            String logFileName = XxlJobFileHelper.makeLogFileName(new Date(callbackModel.getLogDateTime()), callbackModel.getLogId());
            XxlJobContext.setXxlJobContext(new XxlJobContext(
                    -1,
                    null,
                    logFileName,
                    -1,
                    -1));
            XxlJobHelper.log(logContent);
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
