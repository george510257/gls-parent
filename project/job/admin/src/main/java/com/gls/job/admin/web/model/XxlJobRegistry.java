package com.gls.job.admin.web.model;

import lombok.Data;

import java.util.Date;

/**
 * Created by xuxueli on 16/9/30.
 */
@Data
public class XxlJobRegistry {

    private Long id;
    private String registryGroup;
    private String registryKey;
    private String registryValue;
    private Date updateTime;

}
