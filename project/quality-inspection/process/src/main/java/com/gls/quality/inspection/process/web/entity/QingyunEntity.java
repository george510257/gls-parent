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
public class QingyunEntity extends BaseEntity {
    @Comment("目录")
    private String dir;
    @Column(unique = true)
    @Comment("文件")
    private String fileUrl;
    @Comment("callid")
    private String checkNo;
}
