package com.gls.job.admin.web.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author xuxueli 2019-05-04 16:43:12
 */
@Data
public class JobUser {

    private Long id;
    /**
     * 账号
     */
    @NotBlank
    @Size(min = 4, max = 20)
    private String username;
    /**
     * 密码
     */
    @NotBlank
    @Size(min = 4, max = 20)
    private String password;
    /**
     * 角色：0-普通用户、1-管理员
     */
    private int role;
    /**
     * 权限：执行器ID列表，多个逗号分割
     */
    private String permission;

}