package com.gls.generate.code.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author george
 */
@Data
@Accessors(chain = true)
public class TableModel {
    private String basePackageUrl;
    private String entityName;
    private String entityNameLower;
    private String entityComment;
    private List<ColumnModel> columns;
}
