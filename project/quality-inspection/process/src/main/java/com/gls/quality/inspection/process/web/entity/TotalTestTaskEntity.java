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
@Comment("")
public class TotalTestTaskEntity extends BaseEntity {
    @Column
    @Comment("用户id")
    private Long userId;
    @Column
    @Comment("模型id")
    private Long modelId;
    @Column
    @Comment("任务名称")
    private String testName;
    @Column
    @Comment("测试类型 1语义标签，2复合标签，3整通标签")
    private Integer testType;
    @Column
    @Comment("状态 0进行中，1已完成，2失败")
    private Integer status;
    @Column
    @Comment("整通通过率")
    private Double passRate;
}
