package com.gls.framework.api.result;

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
@NoArgsConstructor
@AllArgsConstructor
public class Result<Model> implements Serializable {
    public static final Integer SUCCESS_CODE = 200;
    public static final Integer FAIL_CODE = 500;
    public static final Result<String> SUCCESS = new Result<>(null);
    public static final Result<String> FAIL = new Result<>(FAIL_CODE, null);
    private Integer code;
    private String message;
    private Model model;

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(Model model) {
        this.code = SUCCESS_CODE;
        this.model = model;
    }

    public Result(Result<Model> fail, Model message) {
    }
}
