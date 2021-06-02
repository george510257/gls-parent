package com.gls.job.core.biz.model;

import com.gls.job.core.enums.RegistryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xuxueli
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistryParam implements Serializable {

    private RegistryType registryType;
    private String registryKey;
    private String registryValue;

}
