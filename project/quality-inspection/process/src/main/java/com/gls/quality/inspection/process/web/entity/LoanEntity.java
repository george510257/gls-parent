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
@Comment("阳光推送数据")
public class LoanEntity extends BaseEntity {
    @Column
    @Comment("tb_check主键")
    private Integer checkId;
    @Column
    @Comment("手机号")
    private String telNumber;
    @Column
    @Comment("拨打时间")
    private Date checkTime;
    @Column(length = 200)
    @Comment("电核/视频ID")
    private String checkNo;
    @Column
    @Comment("电核/视频完成时间")
    private Date endTime;
    @Column(length = 16777215)
    @Comment("面签话术")
    private String faceMess;
    @Column
    @Comment("电核/面签操作人")
    private String operateUser;
    @Column
    @Comment("电核/面签操作人姓名")
    private String operateName;
    @Column
    @Comment("所属机构所在分部")
    private String brIdPar;
    @Column(length = 225)
    @Comment("所属机构")
    private String brId;
    @Column(length = 225)
    @Comment("审批组")
    private String groupId;
    @Column
    @Comment("审批处室")
    private String groupName;
    @Column
    @Comment("电核/面签审批结果")
    private String dhSpResult;
    @Column(length = 500)
    @Comment("文件url")
    private String fileUrl;
    @Column
    @Comment("状态，0未被获取，1已获取")
    private Integer status;
    @Column
    @Comment("产品大类：A:信贷 B:车贷 C:房贷")
    private String belType;
    @Column(length = 50)
    @Comment("是否私营人士：是，否（信车房）")
    private String privateEmployer;
    @Column(length = 50)
    @Comment("贷款用途（信车房），生产经营，生活消费")
    private String purpose;
    @Column(length = 50)
    @Comment("缴费方式：期缴  趸交（信车房）")
    private String payType;
    @Column(length = 50)
    @Comment("评分等级：A，B，C，D，E（信车房）")
    private String scoreGrade;
    @Column(length = 50)
    @Comment("车辆资质：是，否（信）")
    private String carQualification;
    @Column(length = 50)
    @Comment("房贷资质：是，否（信）")
    private String houseQualification;
    @Column(length = 50)
    @Comment("保单加资质：是，否（信）")
    private String policyQualification;
}
