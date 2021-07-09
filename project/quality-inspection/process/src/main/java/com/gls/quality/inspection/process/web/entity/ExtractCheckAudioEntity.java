package com.gls.quality.inspection.process.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

/**
 * @author george
 */
@Entity
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Comment("质检计划单条语音表")
public class ExtractCheckAudioEntity extends BaseEntity {
    @Column
    @Comment("客服唯一标识")
    private String customerServiceId;
    @Column
    @Comment("质检ID")
    private Integer extractCheckId;
    @Column(length = 64)
    @Comment("对话ID")
    private String dialogueId;
    @Column
    @Comment("0:待申诉、1:申诉中、2:已复检、3:已过申诉时间")
    private Integer status;
    @Column(length = 1000)
    @Comment("音频文件url或文本文件url")
    private String audioUrl;
    @Column
    @Comment("对话音频时长，单位毫秒")
    private Long duration;
    @Column
    @Comment("对话次数")
    private Integer dialogueNumber;
    @Column
    @Comment("资源类型0：音频，1：文本")
    private Integer resourceType;
    @Column
    @Comment("是否转写 1已翻译，0未翻译")
    private Integer isTranslated;
    @Column
    @Comment("是否查看 1已查看，0未查看")
    private Integer isLooked;
    @Column
    @Comment("客服转写正确率")
    private Integer serviceCheckRate;
    @Column
    @Comment("用户转写正确率")
    private Integer userCheckRate;
    @Column
    @Comment("违规数量")
    private Integer violationCount;
    @Column
    @Comment("复检得分")
    private Double recheckScore;
    @Column
    @Comment("质检评分")
    private Double checkScore;
    @Column
    @Comment("抽检得分")
    private Double spotCheckScore;
    @Column
    @Comment("是否质检，1：已质检，0：未质检")
    private Integer isChecked;
    @Column
    @Comment("是否抽检，1：已抽检，0：未抽检")
    private Integer isSpotChecked;
    @Column
    @Comment("质检时间")
    private Date checkTime;
    @Column(length = 32)
    @Comment("顾客手机号")
    private String customerMobile;
    @Column
    @Comment("是否是无效会话 1是，0否")
    private Integer isInvalid;
    @Column
    @Comment("实际操作复检的质检员id，已复检状态有值，可能为管理员")
    private Integer recheckUserId;
    @Column
    @Comment("申请单号")
    private String applyCode;
    @Column
    @Comment("申诉分配的质检员id")
    private Integer distributeUserId;
}
