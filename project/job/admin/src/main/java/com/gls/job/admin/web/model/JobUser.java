package com.gls.job.admin.web.model;

import com.gls.framework.api.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author george
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class JobUser extends BaseModel {
    /**
     * 账号
     */
    @NotBlank(message = "请输入账号")
    @Size(min = 4, max = 20, message = "长度限制[4-20]")
    private String username;
    /**
     * 密码
     */
    @NotBlank(message = "请输入密码")
    @Size(min = 4, max = 20, message = "长度限制[4-20]")
    private String password;
    /**
     * 角色：0-普通用户、1-管理员
     */
    private Integer role;
    /**
     * 权限：执行器ID列表，多个逗号分割
     */
    private String permission;
}
