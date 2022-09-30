package com.example.productdelivery.config;

import com.example.productdelivery.entity.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {
    private final Long id;
    private final String userName;
    private final String password;
    private final List<GrantedAuthority> authorities;

    public UserDetailsImpl(Users user) {
        this.id= user.getId();
        this.userName = user.getUserName();
        this.password=user.getPassword();
        this.authorities= List.of(new SimpleGrantedAuthority(user.getRole()));
    }
    public Long getId() {
        return id;
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
        return userName;
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
        return true;
    }
}
