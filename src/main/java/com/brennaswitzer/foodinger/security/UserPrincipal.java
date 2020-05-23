package com.brennaswitzer.foodinger.security;

import com.brennaswitzer.foodinger.model.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Getter
public class UserPrincipal implements OAuth2User {

    private static final GrantedAuthority ROLE_USER = new SimpleGrantedAuthority("ROLE_USER");

    public static UserPrincipal of(User user) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(ROLE_USER);
        return new UserPrincipal(
                user.getId(),
                user.getEmail(),
                authorities
        );
    }

    private final Long id;
    private final String email;
    private final Collection<GrantedAuthority> authorities;

    public UserPrincipal(Long id, String email, Collection<GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.authorities = Collections.unmodifiableCollection(
                new ArrayList<>(authorities));
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Collections.emptyMap();
    }

    @Override
    public String getName() {
        return email;
    }

}
