package com.gls.job.core.api.model;

import com.gls.framework.api.model.BaseModel;
import com.gls.job.core.constants.RegistryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author george
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class RegistryModel extends BaseModel {
    private RegistryType registryGroup;
    private String registryKey;
    private String registryValue;
}
