package cn.cy.security.web.model;

import cn.cy.security.enums.UserStatusCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 友叔(xu)
 * @Date: 2020/12/4 16:21
 * @Description: UserDetails 继承类
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SecurityUserDetails implements UserDetails {

    @JsonProperty(value = "user")
    private LoginUser loginUser;
    private List<String> roles;
    private List<String> permissions;

    public SecurityUserDetails() {
    }

    public SecurityUserDetails(LoginUser loginUser) {
        this(
                loginUser,
                loginUser.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList()),
                loginUser.getPermissions().stream().map(Permission::getPermissionName).collect(Collectors.toList())
        );
    }

    public SecurityUserDetails(LoginUser loginUser, List<String> roles, List<String> permissions) {
        this.loginUser = loginUser;
        this.roles = roles;
        this.permissions = permissions;
    }


    /**
     * 获取授权
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return loginUser.getPassword();
    }

    @Override
    public String getUsername() {
        return loginUser.getUsername();
    }

    /**
     * 账户是否过期
     */
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账户是否被锁定冻结
     */
    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 凭证未过期
     */
    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 账户是否开启
     */
    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return loginUser.getStatus().equals(UserStatusCode.ENABLE.getValue());
    }


}
