package com.gls.framework.api.result;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author george
 */
@Data
@Builder
public class Result<Model> implements Serializable {

    private Boolean success;

    private Integer code;

    private String message;

    private Model model;

}
