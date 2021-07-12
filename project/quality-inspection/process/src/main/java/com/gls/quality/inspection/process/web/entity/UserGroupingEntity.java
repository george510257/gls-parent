package com.gls.quality.inspection.process.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author george
 */
@Entity
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Comment("用户分组表")
public class UserGroupingEntity extends BaseEntity {
    @Comment("父级id")
    @ManyToOne
    private UserGroupingEntity parent;
    @Comment("是否显示0不显示，1显示")
    private Integer display;
    @Comment("等级0-10")
    private Integer levels;
    @Comment("")
    private Integer shows;
}
