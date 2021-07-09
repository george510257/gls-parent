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
@Comment("抽检计划表")
public class SpotCheckEntity extends BaseEntity {
    @Column
    @Comment("抽检计划名称")
    private String spotCheckName;
    @Column
    @Comment("质检计划id")
    private Integer extractCheckId;
    @Column
    @Comment("抽检截止时间")
    private Date deadline;
    @Column
    @Comment("抽检进度")
    private Integer spotCheckSchedule;
    @Column
    @Comment("抽检会话量")
    private Integer dialogueNumber;
    @Column
    @Comment("1已失效，0未失效")
    private Integer isInvalided;
    @Column
    @Comment("状态0:待开始，1:执行中，2:已暂停，3:已完成")
    private Integer status;
}
