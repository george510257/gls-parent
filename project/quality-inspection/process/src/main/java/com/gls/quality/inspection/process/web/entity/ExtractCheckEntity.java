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
@Comment("质检计划表")
public class ExtractCheckEntity extends BaseEntity {
    @Column
    @Comment("质检员ID，多个逗号拼接")
    private String userId;
    @Column
    @Comment("质检计划名称")
    private String extractCheckName;
    @Column
    @Comment("是否分角色转写，0:否，1:是")
    private Integer differentiateRole;
    @Column
    @Comment("评分模版ID")
    private Long scoreTemplateId;
    @Column
    @Comment("行业ID")
    private Long industryCategoryId;
    @Column
    @Comment("音频总时长单位秒")
    private Integer totalDuration;
    @Column
    @Comment("模型名称")
    private String modelName;
    @Column
    @Comment("申述截止时间")
    private Date deadline;
    @Column
    @Comment("状态0:质检中，1:暂停，2:完成  3未开始")
    private Integer status;
    @Column
    @Comment("质检进度")
    private Integer extractCheckSchedule;
    @Column
    @Comment("模型ID")
    private Long modelId;
    @Column(length = 1000)
    @Comment("文件URL（未知）")
    private String fileUrl;
    @Column
    @Comment("资源类型0：音频，1：文本")
    private Integer resourceType;
    @Column
    @Comment("是否质检完成，1完成，0未完成")
    private Integer isChecked;
    @Column
    @Comment("是否转写完成，1完成，0未完成")
    private Integer isFinished;
    @Column
    @Comment("质检方式：0/手动质检 1/自动质检")
    private Integer extractCheckType;
    @Column
    @Comment("质检计划开始时间")
    private Date startTime;
    @Column
    @Comment("所选评分模板的基础分数，防止评分模板基础分被修改")
    private Integer baseScore;
    @Column
    @Comment("质检计划完成时间")
    private Date endTime;
    @Column
    @Comment("质检时间")
    private Date checkTime;
}
