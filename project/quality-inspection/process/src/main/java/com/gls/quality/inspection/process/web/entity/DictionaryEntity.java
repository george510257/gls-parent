package com.gls.quality.inspection.process.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

/**
 * @author george
 */
@Entity
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Comment("v2-词库")
public class DictionaryEntity extends BaseEntity {
    @Comment("模型id 0:通用 ")
    @ManyToOne
    private ModelEntity model;
    @Comment("词集类型，1=>同义词 2=>同类词 3=>敏感词")
    private Integer type;
    @Lob
    @Comment("词集内容，词之间用`,`分割，如`宝马,奔驰,奥迪`")
    private String dictContent;
}
