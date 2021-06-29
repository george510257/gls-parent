package com.gls.job.admin.web.model;

import com.gls.framework.api.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author george
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class JobGroup extends BaseModel {
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
