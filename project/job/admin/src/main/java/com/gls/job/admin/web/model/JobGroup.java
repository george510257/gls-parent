package com.gls.job.admin.web.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author xuxueli
 * @date 16/9/30
 */
@Data
public class JobGroup {

    private Long id;
    @NotBlank
    @Size(min = 4, max = 64)
    private String appname;
    @NotBlank
    private String title;

    /**
     * 执行器地址类型：0=自动注册、1=手动录入
     */
    private int addressType;

    /**
     * 执行器地址列表，多地址逗号分隔(手动录入)
     */
    @NotBlank
    private String addressList;

    private Date updateTime;

}
