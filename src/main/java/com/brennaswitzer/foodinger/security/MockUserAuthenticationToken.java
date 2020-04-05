package com.brennaswitzer.foodinger.security;

import com.brennaswitzer.foodinger.model.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

public class MockUserAuthenticationToken extends AbstractAuthenticationToken {

    private final User user;

    public MockUserAuthenticationToken(User user) {
        super(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
        this.user = user;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return user;
    }
}
