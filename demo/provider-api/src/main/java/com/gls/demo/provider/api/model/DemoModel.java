package com.gls.demo.provider.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author george
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DemoModel implements Serializable {

    private Integer id;

    private String name;
}
