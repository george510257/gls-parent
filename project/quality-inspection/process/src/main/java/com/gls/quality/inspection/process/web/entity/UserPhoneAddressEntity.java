package com.gls.quality.inspection.process.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

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
@Comment("用户手机地址")
public class UserPhoneAddressEntity extends BaseEntity {
    @Comment("语音通话表ID")
    @ManyToOne
    private ExtractCheckAudioEntity extractCheckAudio;
    @Comment("城市")
    private String address;
}
