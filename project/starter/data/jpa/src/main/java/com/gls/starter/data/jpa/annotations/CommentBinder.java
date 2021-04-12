package com.gls.starter.data.jpa.annotations;

import org.hibernate.annotations.common.reflection.XClass;
import org.hibernate.annotations.common.reflection.XProperty;
import org.hibernate.cfg.Ejb3Column;
import org.hibernate.cfg.PropertyData;
import org.hibernate.mapping.Table;

/**
 * @author george
 */
public class CommentBinder {

    public static void setTableComment(Table table, XClass xclass) {
        Comment comment = xclass.getAnnotation(Comment.class);
        if (comment != null) {
            table.setComment(comment.value());
        }
    }

    public static void setColumnComment(Ejb3Column column, PropertyData propertyData) {
        XProperty xProperty = propertyData.getProperty();
        if (xProperty != null) {
            Comment comment = xProperty.getAnnotation(Comment.class);
            if (comment != null) {
                column.setComment(comment.value());
            }
        }
    }

}
