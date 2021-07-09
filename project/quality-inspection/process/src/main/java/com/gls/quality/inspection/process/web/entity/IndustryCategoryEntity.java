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
@Comment("配置-知识体系-行业分类")
public class IndustryCategoryEntity extends BaseEntity {
    @Column
    @Comment("等级1-5")
    private Integer level;
    @Column
    @Comment("父ID")
    private Long parentId;
    @Column
    @Comment("是否显示0不显示，1显示")
    private Integer display;
    @Column
    @Comment("是否使用 0未使用，1已使用")
    private Integer isUsed;
}
