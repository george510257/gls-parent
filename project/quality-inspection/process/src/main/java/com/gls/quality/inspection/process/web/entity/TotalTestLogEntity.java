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
public class TotalTestLogEntity extends BaseEntity {
    @Comment("整通测试任务id")
    private Long totalTestTaskId;
    @Column(length = 65535)
    @Comment("测试内容")
    private String testContent;
    @Comment("期望标签")
    private String expectLabel;
    @Comment("期望角色 1，客服  2，顾客")
    private Integer expectRole;
    @Column(length = 50)
    @Comment("模型预测标签")
    private String recLabel;
    @Column(length = 500)
    @Comment("模型预测规则")
    private String recRule;
    @Comment("模型预测角色1，客服 2，顾客")
    private Integer recRole;
    @Comment("模型")
    private String module;
    @Comment("得分")
    private Double score;
    @Comment("是否通过 1.通过 0.不通过")
    private Integer pass;
}
