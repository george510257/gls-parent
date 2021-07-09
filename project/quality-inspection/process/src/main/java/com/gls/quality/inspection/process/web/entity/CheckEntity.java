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
@Comment("阳光推送数据")
public class CheckEntity extends BaseEntity {
    @Column
    @Comment("申请单号")
    private String applyCode;
    @Column(length = 16)
    @Comment("发送环节：01初审拒绝 02初审通过 03初级复议审核通过 04初级复议审核驳回 05合同签订 06视频补录")
    private String node;
    @Column
    @Comment("客户姓名")
    private String customerName;
    @Column
    @Comment("身份证号")
    private String idCard;
    @Column
    @Comment("产品名称")
    private String productName;
    @Column
    @Comment("单位名称")
    private String companyName;
    @Column
    @Comment("质检类型 (审批环节传“音频质检”、签约环节传“签约视频质检”、视频补录环节传“补录视频质检”)")
    private String checkType;
    @Column
    @Comment("状态，0未被获取，1已获取")
    private Integer status;
}
