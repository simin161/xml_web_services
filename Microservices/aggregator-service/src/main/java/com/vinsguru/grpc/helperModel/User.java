package com.vinsguru.grpc.helperModel;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import java.util.Collection;
import java.util.List;

public class User implements UserDetails {
    private String email;
    private String password;
    private List<Authority> authorities;
    private boolean isEnabled;
    public String getEmail() {
        return email;
    }
    private String id;
    private List<Permission> perm;

    public void setPerm(List<Permission> perm) {
        this.perm = perm;
    }

    public List<Permission> getPerm() {
        return perm;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setEmail(String email) {
        this.email = email;
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
        return email;
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
        return isEnabled;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthorities(List<Authority> a) {
        this.authorities = a;
    }

    public void setEnabled(boolean enabled){ this.isEnabled = enabled;}
}
