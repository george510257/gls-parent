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
@Comment("")
public class TotalTestTaskEntity extends BaseEntity {
    @Comment("用户id")
    @ManyToOne
    private UserEntity user;
    @Comment("模型id")
    @ManyToOne
    private ModelEntity model;
    @Comment("任务名称")
    private String testName;
    @Comment("测试类型 1语义标签，2复合标签，3整通标签")
    private Integer testType;
    @Comment("状态 0进行中，1已完成，2失败")
    private Integer status;
    @Comment("整通通过率")
    private Double passRate;
}
