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
@Comment("质检结果表")
public class ExtractCheckResultEntity extends BaseEntity {
    @Column
    @Comment("语音通话表ID")
    private Long extractCheckAudioId;
    @Column(length = 100)
    @Comment("违规项")
    private String violationsItem;
    @Column
    @Comment("扣分")
    private Double violationsScore;
    @Column(length = 50)
    @Comment("语义类别")
    private String category;
    @Column(length = 50)
    @Comment("语音标签违规项目在语音中的起始位置使用'-'分割，例23-59")
    private String paragraph;
    @Column
    @Comment("评分项ID")
    private Long scoreItemId;
    @Column
    @Comment("评分项类型 1:评分项，2:组合评分项")
    private Integer scoreItemType;
    @Column
    @Comment("评分类型 1表示加分项，0表示减分项，2中性项")
    private Integer scoreAttribute;
    @Column
    @Comment("质检对象 1表示客服，2表示顾客，3整通")
    private Integer inspectionObject;
    @Column
    @Comment("关键字信息")
    private String keyInfo;
    @Column
    @Comment("类型  1.语义标签 2.语音标签 3.整通标签 4.复合标签")
    private Integer type;
    @Column
    @Comment("复检状态1:通过，2不通过，0待申诉，3申诉中")
    private Integer recheckStatus;
    @Column(length = 10000)
    @Comment("复检申述理由")
    private String representation;
    @Column
    @Comment("是否是漏检项 1是，0否，2整通漏检项")
    private Integer isMissed;
    @Column
    @Comment("是否误检 1 是，0 否")
    private Integer isError;
    @Column(length = 16)
    @Comment("评分策略")
    private String scoreStrategy;
}
