package com.gls.demo.provider.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author george
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class DemoModel implements Serializable {
    private Integer id;
    private String name;
}
