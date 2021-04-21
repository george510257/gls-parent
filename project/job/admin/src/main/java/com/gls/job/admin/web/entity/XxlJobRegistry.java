package com.gls.job.admin.web.entity;

import com.gls.job.core.api.model.enums.RegistryType;
import lombok.Data;

import java.util.Date;

/**
 * @author george
 * @date 16/9/30
 */
@Data
public class XxlJobRegistry {

    private int id;
    private RegistryType registryType;
    private String registryKey;
    private String registryValue;
    private Date updateTime;

}
