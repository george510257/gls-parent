package com.gls.demo.security.web.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author george
 */
@Data
@Accessors(chain = true)
public class FileModel implements Serializable {
    private String path;
}
