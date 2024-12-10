package com.bd.sc.models.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AuthUser implements UserDetails {
    private Long id;
    private String name;
    private String username;
    private String password;
    private boolean active;
    private Collection<? extends GrantedAuthority>  authorities;

    public AuthUser(Long id, String name, String username, String password, boolean active, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.active = active;
        this.authorities = authorities;
    }

    public static AuthUser build(User user){
        List<GrantedAuthority> auths = user.getRoles().stream().map(rol -> new SimpleGrantedAuthority(rol.getName())).collect(Collectors.toList());
        return new AuthUser(user.getId(), user.getName(), user.getUsername(), user.getPassword(), user.isActive(), auths);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }
}
