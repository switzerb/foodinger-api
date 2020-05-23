package com.brennaswitzer.foodinger.security;

import com.brennaswitzer.foodinger.model.User;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class MockUserAuthenticationToken extends AbstractAuthenticationToken {

    @Getter
    private final UserPrincipal principal;

    public MockUserAuthenticationToken(User user) {
        // tis a silly place
        super(UserPrincipal.of(user).getAuthorities());
        this.principal = UserPrincipal.of(user);
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

}
