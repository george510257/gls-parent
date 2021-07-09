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
public class SingleTestLogEntity extends BaseEntity {
    @Column
    @Comment("用户id")
    private Long userId;
    @Column(length = 1024)
    @Comment("用户问")
    private String ask;
    @Column(length = 2048)
    @Comment("机器答")
    private String answer;
    @Column
    @Comment("对象 1.坐席 2.客户")
    private Integer role;
}
