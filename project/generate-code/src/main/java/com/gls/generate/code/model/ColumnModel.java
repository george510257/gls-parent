package com.gls.generate.code.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author george
 */
@Data
@Accessors(chain = true)
public class ColumnModel {
    private String name;
    private String nameUpper;
    private String type;
    private String typeEntityName;
    private String typeEntityNameLower;
    private String comment;
    private String columnKey;
    private String referencedTableName;
    private String length;
}
