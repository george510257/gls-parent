package com.gls.starter.data.jpa.base;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.generator.SnowflakeGenerator;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * todo 1. 雪花算法配置：需要通过parameters参数设置workerId，dataCenterId。后续通过配置文件设置。
 * todo 2. 数据审计功能：自动更新createdBy、createdDate、lastModifiedBy、lastModifiedDate等字段。
 *
 * @author george
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(generator = SnowflakeGenerator.NAME)
    @GenericGenerator(name = SnowflakeGenerator.NAME, strategy = SnowflakeGenerator.STRATEGY,
            parameters = {
                    @Parameter(name = SnowflakeGenerator.WORKER_ID_NAME, value = "1"),
                    @Parameter(name = SnowflakeGenerator.DATA_CENTER_ID_NAME, value = "1")
            })
    @Comment("主键 ID")
    private Long id;
    @Comment("名称")
    private String name;
    @Version
    @Comment("版本信息")
    private Timestamp timeVersion;
    @Comment("逻辑删除标志 true:已删除; false:未删除")
    private Boolean deleteFlg = false;
    @Comment("租户ID")
    private Long tenantId;
    @Comment("应用ID")
    private Long appId;
    @CreatedBy
    @Comment("创建用户ID")
    private Long createdUserId;
    @CreatedDate
    @Comment("创建时间")
    private Timestamp createdDate;
    @LastModifiedBy
    @Comment("最后修改用户ID")
    private Long updateUserId;
    @LastModifiedDate
    @Comment("最后修改时间")
    private Timestamp updateDate;
}
