package com.gls.framework.api.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author george
 */
@Data
@Accessors(chain = true)
public class BaseModel implements Serializable {
    private Long id;

    public interface SimpleView {
    }

    public interface DetailView extends SimpleView {
    }
}
