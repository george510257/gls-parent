package com.gls.common.auth.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gls.common.user.api.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author george
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsModel implements UserDetails {
    private UserModel userModel;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userModel.getRoles().stream().map(GrantedAuthorityModel::new).collect(Collectors.toList());
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return userModel.getPassword();
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return userModel.getUsername();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return userModel.getAccountNonExpired();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return userModel.getAccountNonLocked();
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return userModel.getCredentialsNonExpired();
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return userModel.getEnabled();
    }
}
