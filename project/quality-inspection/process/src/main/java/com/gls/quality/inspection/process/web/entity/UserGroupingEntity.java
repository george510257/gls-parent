package com.gls.quality.inspection.process.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;

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
    @Column
    @Comment("分组名称")
    private String name;
    @Column
    @Comment("父级id")
    private Integer parentId;
    @Column
    @Comment("是否显示0不显示，1显示")
    private Integer display;
    @Column
    @Comment("等级0-10")
    private Integer level;
    @Column
    @Comment("")
    private Integer show;
}
