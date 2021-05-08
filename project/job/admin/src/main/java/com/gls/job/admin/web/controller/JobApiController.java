package com.gls.job.admin.web.controller;

import com.gls.job.admin.web.controller.annotation.PermissionLimit;
import com.gls.job.core.api.model.CallbackModel;
import com.gls.job.core.api.model.RegistryModel;
import com.gls.job.core.api.model.Result;
import com.gls.job.core.api.rpc.AdminApi;
import com.gls.job.core.util.GsonTool;
import com.gls.job.core.util.JobRemotingUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author george
 * @date 17/5/10
 */
@Controller
@RequestMapping("/api")
public class JobApiController {

    @Resource
    private AdminApi adminApi;

    @Value("${gls.job.accessToken}")
    private String accessToken;

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
        if (accessToken != null
                && accessToken.trim().length() > 0
                && !accessToken.equals(request.getHeader(JobRemotingUtil.GLS_JOB_ACCESS_TOKEN))) {
            return new Result<String>(Result.FAIL_CODE, "The access token is wrong.");
        }

        // services mapping
        if ("callback".equals(uri)) {
            List<CallbackModel> callbackModelList = GsonTool.fromJson(data, List.class, CallbackModel.class);
            return adminApi.callback(callbackModelList);
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
