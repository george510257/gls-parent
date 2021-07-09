package com.gls.quality.inspection.process.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Lob;

/**
 * @author george
 */
@Entity
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Comment("系统管理-默认设置-过滤词汇")
public class ConfigWordFilterEntity extends BaseEntity {
    @Lob
    @Comment("客服词云图过滤")
    private String serviceWordFilter;
    @Lob
    @Comment("客户词云图过滤")
    private String customerWordFilter;
}
