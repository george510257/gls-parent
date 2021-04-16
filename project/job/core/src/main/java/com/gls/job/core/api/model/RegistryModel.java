package com.gls.job.core.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by george on 2017-05-10 20:22:42
 *
 * @author george
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistryModel implements Serializable {

    private String registryGroup;
    private String registryKey;
    private String registryValue;

}
