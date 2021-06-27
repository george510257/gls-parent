package com.gls.common.user.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

/**
 * @author george
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Comment("客户端信息表")
public class ClientEntity extends BaseEntity {
    @Column(unique = true)
    @Comment("客户端ID")
    private String clientId;
    @Comment("客户端密码")
    private String clientSecret;
    @Comment("作用域")
    private String scope;
    @Comment("资源ID")
    private String resourceIds;
    @Comment("授权授予类型")
    private String authorizedGrantTypes;
    @Comment("已注册的重定向URI")
    private String registeredRedirectUris;
    @Comment("自动批准作用域")
    private String autoApproveScopes;
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @Comment("角色列表")
    private List<RoleEntity> roles;
    @Comment("访问令牌有效期秒数")
    private Integer accessTokenValiditySeconds;
    @Comment("刷新令牌有效期秒数")
    private Integer refreshTokenValiditySeconds;
    @Comment("其他信息")
    private String additionalInformation;
}
