package com.gls.quality.inspection.process.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author george
 */
@Entity
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Comment("")
public class HotwordCustomerEntity extends BaseEntity {
    @Id
    @Column
    @Comment("主键")
    private Long id;
    @Column(length = 30)
    @Comment("词语名称")
    private String word;
    @Column
    @Comment("出现的次数")
    private Integer number;
    @Column
    @Comment("质检计划ID")
    private Integer extractCheckId;
    @Column
    @Comment("质检计划质检时间")
    private Date checkTime;
}
