package com.gls.quality.inspection.process.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author george
 */
@Entity
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Comment("")
public class SectionAnnotatedEntity extends BaseEntity {
    @Comment("语料id")
    @ManyToOne
    private CorpusEntity corpus;
    @Column(length = 512)
    @Comment("选择的句子序号,一个String,序号间用逗号分隔, 例: '1,3,4,5', 表示该标注选中了第1, 3, 4, 5行句子")
    private String selection;
    @Column(length = 4096)
    @Comment("整通标签, 数组, 例 ['a','b','c']")
    private String totalLabel;
    @Column(length = 4096)
    @Comment("单个标签, 数组, 例 ['a','b','c']")
    private String singleLabel;
    @Column(length = 4096)
    @Comment("复合标签, json 例 {'复合标签名' : ['子标签1', '子标签2']}")
    private String complexLabel;
    @Column(length = 65535)
    @Comment("扩展问,规则, json, 例 [{'semantic_category':'语义类别','semantic_label':'标签名称','ext_question':['扩展问1','扩展问2'],'rule':['规则1','规则2']}]")
    private String extqRule;
}
