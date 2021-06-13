package com.gls.job.admin.web.controller;

import com.gls.job.admin.core.constants.JobAdminProperties;
import com.gls.job.admin.web.controller.annotation.PermissionLimit;
import com.gls.job.core.api.model.CallbackModel;
import com.gls.job.core.api.model.RegistryModel;
import com.gls.job.core.api.model.Result;
import com.gls.job.core.api.rpc.AdminApi;
import com.gls.job.core.util.GsonTool;
import com.gls.job.core.util.JobRemotingUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author xuxueli
 * @date 17/5/10
 */
@Controller
@RequestMapping("/api")
public class JobApiController {

    @Resource
    private AdminApi adminApi;
    @Resource
    private JobAdminProperties jobAdminProperties;

    /**
     * api
     *
     * @param uri
     * @param data
     * @return
     */
    @RequestMapping("/{uri}")
    @ResponseBody
    @PermissionLimit(limit = false)
    public Result<String> api(HttpServletRequest request, @PathVariable("uri") String uri, @RequestBody(required = false) String data) {

        // valid
        if (!"POST".equalsIgnoreCase(request.getMethod())) {
            return new Result<String>(Result.FAIL_CODE, "invalid request, HttpMethod not support.");
        }
        if (uri == null || uri.trim().length() == 0) {
            return new Result<String>(Result.FAIL_CODE, "invalid request, uri-mapping empty.");
        }
        if (jobAdminProperties.getAccessToken() != null
                && jobAdminProperties.getAccessToken().trim().length() > 0
                && !jobAdminProperties.getAccessToken().equals(request.getHeader(JobRemotingUtil.GLS_JOB_ACCESS_TOKEN))) {
            return new Result<String>(Result.FAIL_CODE, "The access token is wrong.");
        }

        // services mapping
        if ("callback".equals(uri)) {
            List<CallbackModel> callbackParamList = GsonTool.fromJson(data, List.class, CallbackModel.class);
            return adminApi.callback(callbackParamList);
        } else if ("registry".equals(uri)) {
            RegistryModel registryModel = GsonTool.fromJson(data, RegistryModel.class);
            return adminApi.registry(registryModel);
        } else if ("registryRemove".equals(uri)) {
            RegistryModel registryModel = GsonTool.fromJson(data, RegistryModel.class);
            return adminApi.registryRemove(registryModel);
        } else {
            return new Result<String>(Result.FAIL_CODE, "invalid request, uri-mapping(" + uri + ") not found.");
        }

    }

}
