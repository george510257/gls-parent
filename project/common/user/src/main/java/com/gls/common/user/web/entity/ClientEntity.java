package com.gls.common.user.web.entity;

import com.gls.starter.data.jpa.annotations.Comment;
import com.gls.starter.data.jpa.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

/**
 * @author george
 */
@Entity
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Comment("客户端信息表")
public class ClientEntity extends BaseEntity {
    @Column(unique = true)
    @Comment("客户端ID")
    private String clientId;
    @Comment("客户端密码")
    private String clientSecret;
    @Comment("作用域")
    @ElementCollection
    private List<String> scope;
    @Comment("资源ID")
    @ElementCollection
    private List<String> resourceIds;
    @Comment("授权授予类型")
    @ElementCollection
    private List<String> authorizedGrantTypes;
    @Comment("已注册的重定向URI")
    @ElementCollection
    private List<String> registeredRedirectUris;
    @Comment("自动批准作用域")
    @ElementCollection
    private List<String> autoApproveScopes;
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @Comment("角色列表")
    private List<RoleEntity> roles;
    @Comment("访问令牌有效期秒数")
    private Integer accessTokenValiditySeconds;
    @Comment("刷新令牌有效期秒数")
    private Integer refreshTokenValiditySeconds;
    @Comment("其他信息")
    @ElementCollection
    private Map<String, Object> additionalInformation;
}
