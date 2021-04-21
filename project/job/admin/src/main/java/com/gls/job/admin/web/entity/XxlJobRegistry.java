package com.gls.job.admin.web.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by george on 16/9/30.
 */
@Data
public class XxlJobRegistry {

    private int id;
    private String registryGroup;
    private String registryKey;
    private String registryValue;
    private Date updateTime;

}
