package com.gls.job.admin.web.model;

import com.gls.framework.api.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author george
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class JobGroupModel extends BaseModel {
    @NotBlank(message = "请输入AppName")
    @Size(min = 4, max = 64, message = "AppName长度限制为4~64")
    @Pattern(regexp = "[0-9a-zA-Z-_@#]+$", message = "AppName非法")
    private String appname;
    @NotBlank(message = "请输入名称")
    @Pattern(regexp = "[\\u4e00-\\u9fa5]+$", message = "名称非法")
    private String title;
    /**
     * 执行器地址类型：true=自动注册、false=手动录入
     */
    private Boolean addressType;
    /**
     * 执行器地址列表，多地址逗号分隔(手动录入)
     */
    @NotBlank
    @Pattern(regexp = "[0-9a-zA-Z.,]+$", message = "机器地址非法")
    private String addressList;
}
