package com.gls.job.core.api.model;

import com.gls.job.core.api.model.enums.RegistryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author george
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistryModel implements Serializable {

    private RegistryType registryGroup;
    private String registryKey;
    private String registryValue;

}
